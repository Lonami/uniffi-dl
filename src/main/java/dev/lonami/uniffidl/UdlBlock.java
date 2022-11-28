package dev.lonami.uniffidl;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UdlBlock extends AbstractBlock {
    private final SpacingBuilder spacingBuilder;

    protected UdlBlock(@NotNull ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment, SpacingBuilder spacingBuilder) {
        super(node, wrap, alignment);
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();
        while (child != null) {
            if (child.getElementType() != TokenType.WHITE_SPACE) {
                Block block = new UdlBlock(child, Wrap.createWrap(WrapType.NONE, false),
                        Alignment.createAlignment(), spacingBuilder);
                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    @Override
    public Indent getIndent() {
        IElementType ty = myNode.getElementType();
        if (ty == UdlTypes.REGULAR_OPERATION
                || ty == UdlTypes.DICTIONARY_MEMBER
                || ty == UdlTypes.CONSTRUCTOR
                || ty == UdlTypes.ENUM_VALUE_LIST
                || (ty == UdlTypes.EXTENDED_ATTRIBUTE_LIST && myNode.getTextLength() != 0 && myNode.getTreeParent().getTreeParent() != null)) {
            return Indent.getNormalIndent();
        } else {
            return Indent.getNoneIndent();
        }
    }

    @Override
    public @Nullable Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
