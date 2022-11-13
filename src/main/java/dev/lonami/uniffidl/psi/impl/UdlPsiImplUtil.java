package dev.lonami.uniffidl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import dev.lonami.uniffidl.UdlIcons;
import dev.lonami.uniffidl.psi.UdlDefinition;
import dev.lonami.uniffidl.psi.UdlDictionary;
import dev.lonami.uniffidl.psi.UdlElementFactory;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UdlPsiImplUtil {
    public static PsiElement getNameIdentifier(PsiElement element) {
        ASTNode node = element.getNode().findChildByType(UdlTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    public static String getName(PsiElement element) {
        ASTNode node = element.getNode().findChildByType(UdlTypes.IDENTIFIER);
        return node != null ? node.getText() : null;
    }

    public static PsiElement setName(UdlDictionary element, String newName) {
        ASTNode node = element.getNode().findChildByType(UdlTypes.IDENTIFIER);
        if (node != null) {
            UdlDictionary dictionary = UdlElementFactory.createDictionary(element.getProject(), newName);
            PsiElement nameIdentifier = dictionary.getNameIdentifier();
            if (nameIdentifier != null) {
                element.getNode().replaceChild(node, nameIdentifier.getNode());
            }
        }
        return element;
    }

    public static ItemPresentation getPresentation(final UdlDictionary element) {
        return new ItemPresentation() {
            @Override
            public @Nullable String getPresentableText() {
                return element.getName();
            }

            @Override
            public @NlsSafe @Nullable String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile != null ? containingFile.getName() : null;
            }

            @Override
            public @Nullable Icon getIcon(boolean unused) {
                return UdlIcons.FILE;
            }
        };
    }
}
