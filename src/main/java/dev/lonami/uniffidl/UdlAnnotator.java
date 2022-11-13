package dev.lonami.uniffidl;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.psi.PsiElement;
import dev.lonami.uniffidl.psi.UdlExtendedAttributeList;
import org.jetbrains.annotations.NotNull;

public class UdlAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof UdlExtendedAttributeList) {
            annotate((UdlExtendedAttributeList) element, holder);
        }
    }

    private void annotate(@NotNull UdlExtendedAttributeList element, @NotNull AnnotationHolder holder) {
        if (element.getTextLength() != 0) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element.getTextRange())
                    .textAttributes(DefaultLanguageHighlighterColors.METADATA)
                    .create();
        }
    }
}
