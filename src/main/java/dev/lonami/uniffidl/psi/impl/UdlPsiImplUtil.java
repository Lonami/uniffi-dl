package dev.lonami.uniffidl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import dev.lonami.uniffidl.UdlIcons;
import dev.lonami.uniffidl.psi.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UdlPsiImplUtil {
    // Definition

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

    // Dictionary member

    public static PsiElement getNameIdentifier(UdlDictionaryMember element) {
        ASTNode node = element.getLastChild().getNode().findChildByType(UdlTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    public static String getName(UdlDictionaryMember element) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        return nameIdentifier == null ? "" : nameIdentifier.getText();
    }

    public static PsiElement setName(UdlDictionaryMember element, String newName) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        if (nameIdentifier != null) {
            element.getFirstChild().getNode().replaceChild(
                    nameIdentifier.getNode(),
                    UdlElementFactory.createIdentifier(newName).getNode()
            );
        }
        return element;
    }

    public static ItemPresentation getPresentation(final UdlDictionaryMember element) {
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
                return UdlIcons.PROPERTY;
            }
        };
    }


    // Regular operation

    public static PsiElement getNameIdentifier(UdlRegularOperation element) {
        ASTNode node = element.getLastChild().getFirstChild().getFirstChild().getNode().findChildByType(UdlTypes.IDENTIFIER);
        return node != null ? node.getPsi() : null;
    }

    public static String getName(UdlRegularOperation element) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        return nameIdentifier == null ? "" : nameIdentifier.getText();
    }

    public static PsiElement setName(UdlRegularOperation element, String newName) {
        PsiElement nameIdentifier = getNameIdentifier(element);
        if (nameIdentifier != null) {
            element.getFirstChild().getNode().replaceChild(
                    nameIdentifier.getNode(),
                    UdlElementFactory.createIdentifier(newName).getNode()
            );
        }
        return element;
    }

    public static ItemPresentation getPresentation(final UdlRegularOperation element) {
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
            public Icon getIcon(boolean unused) {
                return UdlIcons.METHOD;
            }
        };
    }

    // Constructor

    public static ItemPresentation getPresentation(final UdlConstructor element) {
        return new ItemPresentation() {
            @Override
            public @Nullable String getPresentableText() {
                return "constructor";
            }

            @Override
            public @NlsSafe @Nullable String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile != null ? containingFile.getName() : null;
            }

            @Override
            public Icon getIcon(boolean unused) {
                return UdlIcons.CONSTRUCTOR;
            }
        };
    }
}
