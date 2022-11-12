package dev.lonami.uniffidl.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import dev.lonami.uniffidl.UdlFileType;
import dev.lonami.uniffidl.UdlLanguage;
import org.jetbrains.annotations.NotNull;

public class UdlFile extends PsiFileBase {
    public UdlFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, UdlLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return UdlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "UDL File";
    }
}
