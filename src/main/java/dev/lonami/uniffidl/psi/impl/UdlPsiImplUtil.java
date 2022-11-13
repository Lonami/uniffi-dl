package dev.lonami.uniffidl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import dev.lonami.uniffidl.psi.UdlDictionary;
import dev.lonami.uniffidl.psi.UdlElementFactory;
import dev.lonami.uniffidl.psi.UdlTypes;

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
}
