package dev.lonami.uniffidl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import dev.lonami.uniffidl.UdlIcons;
import dev.lonami.uniffidl.psi.UdlDefinition;
import dev.lonami.uniffidl.psi.UdlElementFactory;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UdlPsiImplUtil {
    public static PsiElement getNameIdentifier(UdlDefinition element) {
        ASTNode node = element.getFirstChild().getNode().findChildByType(UdlTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    public static String getName(UdlDefinition element) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        return nameIdentifier == null ? "" : nameIdentifier.getText();
    }

    public static PsiElement setName(UdlDefinition element, String newName) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        if (nameIdentifier != null) {
            element.getFirstChild().getNode().replaceChild(
                    nameIdentifier.getNode(),
                    UdlElementFactory.createIdentifier(newName).getNode()
            );
        }
        return element;
    }

    public static ItemPresentation getPresentation(final UdlDefinition element) {
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
                if (element.getCallbackOrInterfaceOrMixin() != null) {
                    if (element.getCallbackOrInterfaceOrMixin().getInterfaceOrMixin() != null) {
                        return UdlIcons.INTERFACE;
                    } else {
                        return UdlIcons.CALLBACK;
                    }
                } else if (element.getNamespace() != null) {
                    return UdlIcons.NAMESPACE;
                } else if (element.getPartial() != null) {
                    return UdlIcons.PARTIAL;
                } else if (element.getDictionary() != null) {
                    return UdlIcons.DICTIONARY;
                } else if (element.getEnum() != null) {
                    return UdlIcons.ENUM;
                } else if (element.getTypedef() != null) {
                    return UdlIcons.TYPE_DEF;
                }
                return null;
            }
        };
    }
}
