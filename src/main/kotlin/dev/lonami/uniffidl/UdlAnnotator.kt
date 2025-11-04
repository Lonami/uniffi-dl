package dev.lonami.uniffidl

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import dev.lonami.uniffidl.psi.UdlDistinguishableType
import dev.lonami.uniffidl.psi.UdlExtendedAttributeList

class UdlAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is UdlExtendedAttributeList -> annotate(element, holder)
            is UdlDistinguishableType -> annotate(element, holder)
        }
    }

    private fun annotate(element: UdlExtendedAttributeList, holder: AnnotationHolder) {
        if (element.textLength != 0) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element.textRange)
                .textAttributes(DefaultLanguageHighlighterColors.METADATA)
                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                .withFix(UdlCreateDictionaryQuickFix(element.text))
                .create()
        }
    }

    private fun annotate(element: UdlDistinguishableType, holder: AnnotationHolder) {
        val identifier = element.identifier
        if (identifier != null) {
            val type = identifier.text
            if (UdlUtil.findTypeDefinitions(element.project, type).isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Cannot resolve symbol: '$type'")
                    .range(identifier.textRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(UdlCreateDictionaryQuickFix(type))
                    .create()
            } else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(identifier.textRange)
                    .textAttributes(UdlSyntaxHighlighter.IDENTIFIER)
                    .create()
            }
        }
    }
}
