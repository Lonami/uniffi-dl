package dev.lonami.uniffidl;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UdlFileType extends LanguageFileType {
    public static final UdlFileType INSTANCE = new UdlFileType();

    private UdlFileType() {
        super(UdlLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "UDL File";
    }

    @Override
    public @NotNull String getDescription() {
        return "UniFFI data language file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "udl";
    }

    @Override
    public @Nullable Icon getIcon() {
        return UdlIcons.FILE;
    }
}
