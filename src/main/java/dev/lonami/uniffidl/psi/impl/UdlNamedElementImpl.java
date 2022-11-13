package dev.lonami.uniffidl.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import dev.lonami.uniffidl.psi.UdlNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class UdlNamedElementImpl extends ASTWrapperPsiElement implements UdlNamedElement {
    public UdlNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
