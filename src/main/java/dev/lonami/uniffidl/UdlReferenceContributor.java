package dev.lonami.uniffidl;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiLiteralValue;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import dev.lonami.uniffidl.psi.UdlDictionary;
import org.jetbrains.annotations.NotNull;

import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;

public class UdlReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(UdlDictionary.class), new PsiReferenceProvider() {
            @Override
            public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                UdlDictionary dictionary = (UdlDictionary) element;
                PsiElement identifier = dictionary.getNameIdentifier();
                if (identifier != null) {
                    return new PsiReference[]{new UdlReference(element, dictionary.getNameIdentifier().getTextRange())};
                }
                return PsiReference.EMPTY_ARRAY;
            }
        });
    }
}
