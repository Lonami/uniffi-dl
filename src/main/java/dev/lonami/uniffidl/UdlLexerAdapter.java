package dev.lonami.uniffidl;

import com.intellij.lexer.FlexAdapter;

public class UdlLexerAdapter extends FlexAdapter {
    public UdlLexerAdapter() {
        super(new UdlLexer(null));
    }
}
