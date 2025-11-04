package dev.lonami.uniffidl.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import dev.lonami.uniffidl.psi.UdlNamedElement

abstract class UdlNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), UdlNamedElement
