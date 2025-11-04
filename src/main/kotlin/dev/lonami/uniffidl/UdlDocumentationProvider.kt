package dev.lonami.uniffidl

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiElement
import com.intellij.psi.presentation.java.SymbolPresentationUtil
import dev.lonami.uniffidl.psi.UdlDefinition
import org.jetbrains.annotations.Nls

class UdlDocumentationProvider : AbstractDocumentationProvider() {
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): @Nls String? {
        if (element is UdlDefinition) {
            val name = element.name
            val file = SymbolPresentationUtil.getFilePathPresentation(element.containingFile)
            val docComment = UdlUtil.findDocumentationComment(element)
            return renderFullDoc(UdlUtil.getDefinitionTypeText(element), name, file, docComment)
        }
        return null
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): @Nls String? {
        return generateDoc(element, originalElement)
    }

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): @Nls String? {
        if (element is UdlDefinition) {
            val name = element.name
            val file = SymbolPresentationUtil.getFilePathPresentation(element.containingFile)
            return "'$name' in $file"
        }
        return null
    }

    private fun renderFullDoc(type: String, name: String, file: String, docComment: String): String {
        val sb = StringBuilder()
        sb.append(DocumentationMarkup.DEFINITION_START)
        sb.append(type)
        sb.append(DocumentationMarkup.DEFINITION_END)
        sb.append(DocumentationMarkup.CONTENT_START)
        sb.append(name)
        sb.append(DocumentationMarkup.CONTENT_END)
        sb.append(DocumentationMarkup.SECTIONS_START)
        addKeyValueSection("Name:", name, sb)
        addKeyValueSection("File:", file, sb)
        addKeyValueSection("Comment:", docComment, sb)
        sb.append(DocumentationMarkup.SECTIONS_END)
        return sb.toString()
    }

    private fun addKeyValueSection(key: String, value: String, sb: StringBuilder) {
        sb.append(DocumentationMarkup.SECTION_HEADER_START)
        sb.append(key)
        sb.append(DocumentationMarkup.SECTION_SEPARATOR)
        sb.append("<p>")
        sb.append(value)
        sb.append(DocumentationMarkup.SECTION_END)
    }
}
