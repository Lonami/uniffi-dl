package dev.lonami.uniffidl.psi

import com.intellij.psi.tree.IElementType
import dev.lonami.uniffidl.UdlLanguage
import org.jetbrains.annotations.NonNls

class UdlTokenType(@NonNls debugName: String) : IElementType(debugName, UdlLanguage.INSTANCE) {
    override fun toString(): String = "UdlTokenType.${super.toString()}"
}
