package dev.lonami.uniffidl

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import dev.lonami.uniffidl.psi.UdlTypes

class UdlBlock(
    node: ASTNode,
    wrap: Wrap?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, null) {

    override fun buildChildren(): List<Block> {
        val blocks = mutableListOf<Block>()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != TokenType.WHITE_SPACE) {
                val block = UdlBlock(child, Wrap.createWrap(WrapType.NONE, false), spacingBuilder)
                blocks.add(block)
            }
            child = child.treeNext
        }
        return blocks
    }

    override fun getIndent(): Indent {
        val ty = myNode.elementType
        return when {
            ty == UdlTypes.REGULAR_OPERATION ||
            ty == UdlTypes.DICTIONARY_MEMBER ||
            ty == UdlTypes.CONSTRUCTOR ||
            ty == UdlTypes.ENUM_VALUE_LIST ||
            (ty == UdlTypes.EXTENDED_ATTRIBUTE_LIST && myNode.textLength != 0 && myNode.treeParent.treeParent != null) -> {
                Indent.getNormalIndent()
            }
            else -> Indent.getNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}
