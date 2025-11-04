package dev.lonami.uniffidl

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import dev.lonami.uniffidl.psi.*
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls

class UdlFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            UdlLexerAdapter(),
            UdlTokenSets.IDENTIFIERS,
            UdlTokenSets.COMMENTS,
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): @NonNls String? = null

    override fun getType(element: PsiElement): @Nls String {
        return when (element) {
            is UdlTypedef -> "typedef"
            is UdlDictionary -> "dictionary"
            is UdlEnum -> "enum"
            else -> ""
        }
    }

    override fun getDescriptiveName(element: PsiElement): @Nls String {
        if (element is UdlDefinition) {
            val name = element.name
            return name ?: ""
        }
        return ""
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): @Nls String {
        if (element is UdlDefinition) {
            val text = element.text
            return text ?: ""
        }
        return ""
    }
}
