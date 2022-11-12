package dev.lonami.uniffidl.psi;

import com.intellij.psi.tree.TokenSet;

public interface UdlTokenSets {
    TokenSet IDENTIFIERS = TokenSet.create(UdlTypes.IDENTIFIER);
    TokenSet COMMENTS = TokenSet.create(UdlTypes.COMMENT);
    TokenSet STRINGS = TokenSet.create(UdlTypes.STRING);
}
