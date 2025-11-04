package dev.lonami.uniffidl.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import dev.lonami.uniffidl.UdlFileType
import dev.lonami.uniffidl.UdlLanguage

class UdlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, UdlLanguage.INSTANCE) {
    override fun getFileType(): FileType = UdlFileType.INSTANCE

    override fun toString(): String = "UDL File"
}
