package dev.lonami.uniffidl.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import dev.lonami.uniffidl.UdlIcons
import dev.lonami.uniffidl.psi.*
import javax.swing.Icon

object UdlPsiImplUtil {
    // Definition
    @JvmStatic
    fun getNameIdentifier(element: UdlDefinition): PsiElement? {
        val identifierParent = if (element.callbackOrInterfaceOrMixin != null) {
            element.firstChild.lastChild.firstChild
        } else {
            element.firstChild
        }
        val node = identifierParent.node.findChildByType(UdlTypes.IDENTIFIER)
        return node?.psi
    }

    @JvmStatic
    fun getName(element: UdlDefinition): String {
        val nameIdentifier = getNameIdentifier(element)
        return nameIdentifier?.text ?: ""
    }

    @JvmStatic
    fun setName(element: UdlDefinition, newName: String): PsiElement {
        val nameIdentifier = getNameIdentifier(element)
        if (nameIdentifier != null) {
            element.firstChild.node.replaceChild(
                nameIdentifier.node,
                UdlElementFactory.createIdentifier(newName).node
            )
        }
        return element
    }

    @JvmStatic
    fun getPresentation(element: UdlDefinition): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String? = element.name

            override fun getLocationString(): @NlsSafe String? {
                val containingFile = element.containingFile
                return containingFile?.name
            }

            override fun getIcon(unused: Boolean): Icon? {
                return when {
                    element.callbackOrInterfaceOrMixin != null -> {
                        if (element.callbackOrInterfaceOrMixin?.interfaceOrMixin != null) {
                            UdlIcons.INTERFACE
                        } else {
                            UdlIcons.CALLBACK
                        }
                    }
                    element.namespace != null -> UdlIcons.NAMESPACE
                    element.partial != null -> UdlIcons.PARTIAL
                    element.dictionary != null -> UdlIcons.DICTIONARY
                    element.enum != null -> UdlIcons.ENUM
                    element.typedef != null -> UdlIcons.TYPE_DEF
                    else -> null
                }
            }
        }
    }

    // Dictionary member
    @JvmStatic
    fun getNameIdentifier(element: UdlDictionaryMember): PsiElement? {
        val node = element.lastChild.node.findChildByType(UdlTypes.IDENTIFIER)
        return node?.psi
    }

    @JvmStatic
    fun getName(element: UdlDictionaryMember): String {
        val nameIdentifier = getNameIdentifier(element)
        return nameIdentifier?.text ?: ""
    }

    @JvmStatic
    fun setName(element: UdlDictionaryMember, newName: String): PsiElement {
        val nameIdentifier = getNameIdentifier(element)
        if (nameIdentifier != null) {
            element.firstChild.node.replaceChild(
                nameIdentifier.node,
                UdlElementFactory.createIdentifier(newName).node
            )
        }
        return element
    }

    @JvmStatic
    fun getPresentation(element: UdlDictionaryMember): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String? = element.name

            override fun getLocationString(): @NlsSafe String? {
                val containingFile = element.containingFile
                return containingFile?.name
            }

            override fun getIcon(unused: Boolean): Icon = UdlIcons.PROPERTY
        }
    }

    // Regular operation
    @JvmStatic
    fun getNameIdentifier(element: UdlRegularOperation): PsiElement? {
        val operationName = element.lastChild.firstChild?.firstChild ?: return null
        val node = operationName.node.findChildByType(UdlTypes.IDENTIFIER)
        return node?.psi
    }

    @JvmStatic
    fun getName(element: UdlRegularOperation): String {
        val nameIdentifier = getNameIdentifier(element)
        return nameIdentifier?.text ?: ""
    }

    @JvmStatic
    fun setName(element: UdlRegularOperation, newName: String): PsiElement {
        val nameIdentifier = getNameIdentifier(element)
        if (nameIdentifier != null) {
            element.firstChild.node.replaceChild(
                nameIdentifier.node,
                UdlElementFactory.createIdentifier(newName).node
            )
        }
        return element
    }

    @JvmStatic
    fun getPresentation(element: UdlRegularOperation): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String? = element.name

            override fun getLocationString(): @NlsSafe String? {
                val containingFile = element.containingFile
                return containingFile?.name
            }

            override fun getIcon(unused: Boolean): Icon = UdlIcons.METHOD
        }
    }

    // Constructor
    @JvmStatic
    fun getPresentation(element: UdlConstructor): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String = "constructor"

            override fun getLocationString(): @NlsSafe String? {
                val containingFile = element.containingFile
                return containingFile?.name
            }

            override fun getIcon(unused: Boolean): Icon = UdlIcons.CONSTRUCTOR
        }
    }
}
