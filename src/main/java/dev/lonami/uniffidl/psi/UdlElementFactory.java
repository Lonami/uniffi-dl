package dev.lonami.uniffidl.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import dev.lonami.uniffidl.UdlFileType;

public class UdlElementFactory {
    public static UdlDictionary createDictionary(Project project, String name) {
        UdlFile file = createFile(project, "dictionary " + name + " {};");
        return file.findChildByClass(UdlDefinition.class).getDictionary();
    }

    public static UdlFile createFile(Project project, String text) {
        String name = "dummy.udl";
        return (UdlFile) PsiFileFactory.getInstance(project).createFileFromText(name, UdlFileType.INSTANCE, text);
    }
}
