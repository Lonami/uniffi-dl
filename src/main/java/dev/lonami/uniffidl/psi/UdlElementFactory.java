package dev.lonami.uniffidl.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import dev.lonami.uniffidl.UdlFileType;

import java.util.Objects;

public class UdlElementFactory {
    public static UdlDictionary createDictionary(Project project, String name) {
        final UdlFile file = createFile(project, "dictionary " + name + " {};");
        return Objects.requireNonNull(file.findChildByClass(UdlDefinition.class)).getDictionary();
    }

    public static PsiElement createIdentifier(String identifier) {
        return new LeafPsiElement(UdlTypes.IDENTIFIER, identifier);
    }

    public static PsiElement createLf(Project project) {
        final UdlFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

    public static UdlFile createFile(Project project, String text) {
        String name = "dummy.udl";
        return (UdlFile) PsiFileFactory.getInstance(project).createFileFromText(name, UdlFileType.INSTANCE, text);
    }
}
