package dev.lonami.uniffidl;

import com.intellij.lang.Language;

public class UdlLanguage extends Language {
    public static final UdlLanguage INSTANCE = new UdlLanguage();

    private UdlLanguage() {
        super("Uniffi-DL");
    }
}
