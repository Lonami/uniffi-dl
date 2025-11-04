package dev.lonami.uniffidl.psi

import com.intellij.psi.tree.IElementType
import dev.lonami.uniffidl.UdlLanguage
import org.jetbrains.annotations.NonNls

class UdlElementType(@NonNls debugName: String) : IElementType(debugName, UdlLanguage.INSTANCE)
