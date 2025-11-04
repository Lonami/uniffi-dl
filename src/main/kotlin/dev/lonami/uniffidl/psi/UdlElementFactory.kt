package dev.lonami.uniffidl.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.source.tree.LeafPsiElement
import dev.lonami.uniffidl.UdlFileType

object UdlElementFactory {
    @JvmStatic
    fun createDictionary(project: Project, name: String): UdlDictionary? {
        val file = createFile(project, "dictionary $name {};")
        return file.findChildByClass(UdlDefinition::class.java)?.dictionary
    }

    @JvmStatic
    fun createIdentifier(identifier: String): PsiElement {
        return LeafPsiElement(UdlTypes.IDENTIFIER, identifier)
    }

    @JvmStatic
    fun createLf(project: Project): PsiElement? {
        val file = createFile(project, "\n")
        return file.firstChild
    }

    @JvmStatic
    fun createFile(project: Project, text: String): UdlFile {
        val name = "dummy.udl"
        return PsiFileFactory.getInstance(project).createFileFromText(name, UdlFileType.INSTANCE, text) as UdlFile
    }
}
