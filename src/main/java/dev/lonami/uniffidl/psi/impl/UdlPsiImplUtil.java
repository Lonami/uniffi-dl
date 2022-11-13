package dev.lonami.uniffidl.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import dev.lonami.uniffidl.psi.UdlTypes;

public class UdlPsiImplUtil {
    public static String getName(PsiElement element) {
        ASTNode node = element.getNode().findChildByType(UdlTypes.IDENTIFIER);
        if (node != null) {
            return node.getText();
        } else {
            return null;
        }
    }
}
