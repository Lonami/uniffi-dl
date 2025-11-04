package dev.lonami.uniffidl

import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class UdlFileType private constructor() : LanguageFileType(UdlLanguage.INSTANCE) {

    override fun getName(): @NonNls String = "UDL File"

    override fun getDescription(): String = "UniFFI data language file"

    override fun getDefaultExtension(): String = "udl"

    override fun getIcon(): Icon = UdlIcons.FILE

    companion object {
        @JvmStatic
        val INSTANCE = UdlFileType()
    }
}
