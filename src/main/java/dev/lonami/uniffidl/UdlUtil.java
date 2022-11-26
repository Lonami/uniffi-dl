package dev.lonami.uniffidl;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import dev.lonami.uniffidl.psi.UdlDefinition;
import dev.lonami.uniffidl.psi.UdlFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class UdlUtil {
    private static boolean isTypeDefinition(UdlDefinition definition) {
        return definition.getTypedef() != null || definition.getDictionary() != null || definition.getEnum() != null;
    }

    protected static String getDefinitionTypeText(UdlDefinition definition) {
        return definition.getFirstChild().getFirstChild().getText();
    }

    /**
     * Search the entire project for type definitions (typedef, dictionary or enum) matching the given key.
     *
     * @param project haystack
     * @param key     needle
     * @return matching definitions
     */
    public static List<UdlDefinition> findTypeDefinitions(Project project, String key) {
        return findDefinitions(project, definition -> isTypeDefinition(definition) && key.equals(definition.getName()));
    }

    public static List<UdlDefinition> findTypeDefinitions(Project project) {
        return findDefinitions(project, UdlUtil::isTypeDefinition);
    }

    public static List<UdlDefinition> findDefinitions(UdlFile udlFile) {
        return findDefinitions(udlFile, null, null);
    }

    private static List<UdlDefinition> findDefinitions(Project project, Predicate<UdlDefinition> filter) {
        List<UdlDefinition> result = new ArrayList<>();

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(UdlFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            UdlFile udlFile = (UdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            result = findDefinitions(udlFile, result, filter);
        }

        return result;
    }

    private static List<UdlDefinition> findDefinitions(UdlFile udlFile, List<UdlDefinition> result, Predicate<UdlDefinition> filter) {
        if (result == null) {
            result = new ArrayList<>();
        }

        if (udlFile != null) {
            UdlDefinition[] definitions = PsiTreeUtil.getChildrenOfType(udlFile, UdlDefinition.class);
            if (definitions != null) {
                for (UdlDefinition definition : definitions) {
                    if (filter == null || filter.test(definition)) {
                        result.add(definition);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Attempt to collect any comment elements above the definition into a string.
     *
     * @param definition point before which to search for comments
     * @return comments' contents
     */
    public static @NotNull String findDocumentationComment(UdlDefinition definition) {
        List<String> result = new LinkedList<>();

        PsiElement element = definition.getPrevSibling().getPrevSibling();
        while (element instanceof PsiComment || element instanceof PsiWhiteSpace) {
            if (element instanceof PsiComment) {
                String commentText = element.getText().replaceFirst("/[/*]+", "");
                int len = commentText.length();
                if (len >= 2 && commentText.charAt(len - 1) == '/' && commentText.charAt(len - 2) == '*') {
                    len -= 2;
                    while (commentText.charAt(len) == '*') {
                        len -= 1;
                    }
                    commentText = commentText.substring(0, len);
                }
                result.add(commentText);
            }
            element = element.getPrevSibling();
        }

        return StringUtil.join(Lists.reverse(result), "\n ");
    }
}
