package dev.lonami.uniffidl

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import dev.lonami.uniffidl.psi.UdlDefinition

class UdlReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val key: String = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val project = myElement.project
        val definitions = UdlUtil.findTypeDefinitions(project, key)
        val results = mutableListOf<ResolveResult>()
        for (definition in definitions) {
            results.add(PsiElementResolveResult(definition))
        }
        return results.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        val project = myElement.project
        val definitions = UdlUtil.findTypeDefinitions(project)
        val variants = mutableListOf<LookupElement>()
        for (definition in definitions) {
            val name = definition.name
            if (name != null && name.isNotEmpty()) {
                variants.add(
                    LookupElementBuilder.create(definition)
                        .withIcon(UdlIcons.FILE)
                        .withTypeText(definition.containingFile.name)
                )
            }
        }
        return variants.toTypedArray()
    }
}
