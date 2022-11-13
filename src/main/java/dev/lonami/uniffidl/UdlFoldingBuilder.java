package dev.lonami.uniffidl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import dev.lonami.uniffidl.psi.UdlDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UdlFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();

        Collection<UdlDictionary> dictionaries = PsiTreeUtil.findChildrenOfType(root, UdlDictionary.class);
        for (final UdlDictionary dictionary : dictionaries) {
            descriptors.add(new FoldingDescriptor(dictionary.getNode(), dictionary.getTextRange()));
        }

        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        String result = "...";
        if (node.getPsi() instanceof UdlDictionary) {
            UdlDictionary dictionary = (UdlDictionary) node.getPsi();
            String name = dictionary.getName();
            if (name != null) {
                result = "dictionary " + name + " { ... }";
            }
        }
        return result;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
