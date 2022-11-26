package dev.lonami.uniffidl;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import dev.lonami.uniffidl.psi.UdlDefinition;
import org.jetbrains.annotations.NotNull;

public class UdlReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(UdlDefinition.class), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                UdlDefinition definition = (UdlDefinition) element;
                PsiElement identifier = definition.getNameIdentifier();
                if (identifier != null) {
                    return new PsiReference[]{new UdlReference(element, definition.getNameIdentifier().getTextRange())};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });
    }
}
