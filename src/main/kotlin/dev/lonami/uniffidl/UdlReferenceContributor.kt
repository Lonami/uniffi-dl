package dev.lonami.uniffidl

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import dev.lonami.uniffidl.psi.UdlDefinition

class UdlReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(UdlDefinition::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    val definition = element as UdlDefinition
                    val identifier = definition.nameIdentifier
                    return if (identifier != null) {
                        arrayOf(UdlReference(element, identifier.textRange))
                    } else {
                        PsiReference.EMPTY_ARRAY
                    }
                }
            }
        )
    }
}
