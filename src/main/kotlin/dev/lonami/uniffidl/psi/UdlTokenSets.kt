package dev.lonami.uniffidl.psi

import com.intellij.psi.tree.TokenSet

object UdlTokenSets {
    @JvmField
    val IDENTIFIERS: TokenSet = TokenSet.create(UdlTypes.IDENTIFIER)
    @JvmField
    val COMMENTS: TokenSet = TokenSet.create(UdlTypes.COMMENT)
    @JvmField
    val STRINGS: TokenSet = TokenSet.create(UdlTypes.STRING)
}
