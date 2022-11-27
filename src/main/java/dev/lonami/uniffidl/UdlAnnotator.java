package dev.lonami.uniffidl;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.psi.PsiElement;
import dev.lonami.uniffidl.psi.UdlDistinguishableType;
import dev.lonami.uniffidl.psi.UdlExtendedAttributeList;
import org.jetbrains.annotations.NotNull;

public class UdlAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof UdlExtendedAttributeList) {
            annotate((UdlExtendedAttributeList) element, holder);
        } else if (element instanceof UdlDistinguishableType) {
            annotate((UdlDistinguishableType) element, holder);
        }
    }

    private void annotate(@NotNull UdlExtendedAttributeList element, @NotNull AnnotationHolder holder) {
        if (element.getTextLength() != 0) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element.getTextRange())
                    .textAttributes(DefaultLanguageHighlighterColors.METADATA)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    .withFix(new UdlCreateDictionaryQuickFix(element.getText()))
                    .create();
        }
    }

    private void annotate(@NotNull UdlDistinguishableType element, @NotNull AnnotationHolder holder) {
        PsiElement identifier = element.getIdentifier();
        if (identifier != null) {
            String type = identifier.getText();
            if (UdlUtil.findTypeDefinitions(element.getProject(), type).isEmpty()) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Cannot resolve symbol: '" + type + "'")
                        .range(identifier.getTextRange())
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .withFix(new UdlCreateDictionaryQuickFix(type))
                        .create();
            } else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .range(identifier.getTextRange())
                        .textAttributes(UdlSyntaxHighlighter.IDENTIFIER)
                        .create();
            }
        }
    }
}
