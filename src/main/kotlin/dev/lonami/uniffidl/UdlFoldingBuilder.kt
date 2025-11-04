package dev.lonami.uniffidl

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import dev.lonami.uniffidl.psi.UdlDefinition

class UdlFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()

        val definitions = PsiTreeUtil.findChildrenOfType(root, UdlDefinition::class.java)
        for (definition in definitions) {
            descriptors.add(FoldingDescriptor(definition.node, definition.textRange))
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        var result = "..."
        if (node.psi is UdlDefinition) {
            val definition = node.psi as UdlDefinition
            result = "${UdlUtil.getDefinitionTypeText(definition)} ${definition.name} { ... }"
        }
        return result
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
