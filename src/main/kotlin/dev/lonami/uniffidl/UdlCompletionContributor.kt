package dev.lonami.uniffidl

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import dev.lonami.uniffidl.psi.UdlTypes

class UdlCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(UdlTypes.IDENTIFIER),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    result.addElement(LookupElementBuilder.create("namespace"))
                    result.addElement(LookupElementBuilder.create("dictionary"))
                    result.addElement(LookupElementBuilder.create("interface"))
                }
            }
        )
    }
}
