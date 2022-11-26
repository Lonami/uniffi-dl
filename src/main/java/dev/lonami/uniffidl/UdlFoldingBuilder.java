package dev.lonami.uniffidl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import dev.lonami.uniffidl.psi.UdlDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UdlFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        Collection<UdlDefinition> definitions = PsiTreeUtil.findChildrenOfType(root, UdlDefinition.class);
        for (final UdlDefinition definition : definitions) {
            descriptors.add(new FoldingDescriptor(definition.getNode(), definition.getTextRange()));
        }

        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        String result = "...";
        if (node.getPsi() instanceof UdlDefinition) {
            UdlDefinition definition = (UdlDefinition) node.getPsi();
            result = UdlUtil.getDefinitionTypeText(definition) + " " + definition.getName() + " { ... }";
        }
        return result;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
