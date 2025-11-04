package dev.lonami.uniffidl

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import dev.lonami.uniffidl.psi.UdlTypes

class UdlBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = PAIRS

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int = openingBraceOffset

    companion object {
        private val PAIRS = arrayOf(
            // TODO angle brackets don't seem to work, see the following link for details:
            //      https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000393430-Auto-closing-braces-in-a-custom-language-BraceMatcher-not-working
            BracePair(UdlTypes.OP_OPEN_ANGLE_BRACKET, UdlTypes.OP_CLOSE_ANGLE_BRACKET, false),
            BracePair(UdlTypes.OP_OPEN_PAREN, UdlTypes.OP_CLOSE_PAREN, false),
            BracePair(UdlTypes.OP_OPEN_BRACKET, UdlTypes.OP_CLOSE_BRACKET, false),
            BracePair(UdlTypes.OP_OPEN_BRACE, UdlTypes.OP_CLOSE_BRACE, true)
        )
    }
}
