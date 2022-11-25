package dev.lonami.uniffidl;

import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import dev.lonami.uniffidl.psi.UdlDefinition;
import dev.lonami.uniffidl.psi.UdlDictionary;
import dev.lonami.uniffidl.psi.UdlFile;
import org.jetbrains.annotations.NotNull;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UdlUtil {
    /**
     * Search the entire project for dictionary definitions matching the given key.
     *
     * @param project haystack
     * @param key     needle
     * @return matching dictionaries
     */
    public static List<UdlDictionary> findDictionaries(Project project, String key) {
        List<UdlDictionary> result = new ArrayList<>();

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(UdlFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            UdlFile udlFile = (UdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (udlFile != null) {
                UdlDefinition[] definitions = PsiTreeUtil.getChildrenOfType(udlFile, UdlDefinition.class);
                if (definitions != null) {
                    for (UdlDefinition definition : definitions) {
                        if (definition.getDictionary() != null && key.equals(definition.getDictionary().getName())) {
                            result.add(definition.getDictionary());
                        }
                    }
                }
            }
        }

        return result;
    }

    public static List<UdlDictionary> findDictionaries(Project project) {
        List<UdlDictionary> result = new ArrayList<>();

        Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(UdlFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            UdlFile udlFile = (UdlFile) PsiManager.getInstance(project).findFile(virtualFile);
            result = findDictionaries(udlFile, result);
        }

        return result;
    }

    public static List<UdlDictionary> findDictionaries(UdlFile udlFile, List<UdlDictionary> result) {
        if (result == null) {
            result = new ArrayList<>();
        }

        if (udlFile != null) {
            UdlDefinition[] definitions = PsiTreeUtil.getChildrenOfType(udlFile, UdlDefinition.class);
            if (definitions != null) {
                for (UdlDefinition definition : definitions) {
                    if (definition.getDictionary() != null) {
                        result.add(definition.getDictionary());
                    }
                }
            }
        }
        return result;
    }

    /**
     * Attempt to collect any comment elements above the dictionary into a string.
     *
     * @param dictionary point before which to search for comments
     * @return comments' contents
     */
    public static @NotNull String findDocumentationComment(UdlDictionary dictionary) {
        List<String> result = new LinkedList<>();

        PsiElement element = dictionary.getParent().getPrevSibling().getPrevSibling();
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
