package dev.lonami.uniffidl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import dev.lonami.uniffidl.psi.UdlDictionary;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UdlReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private final String key;

    public UdlReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<UdlDictionary> dictionaries = UdlUtil.findDictionaries(project, key);
        List<ResolveResult> results = new ArrayList<>();
        for (UdlDictionary dictionary : dictionaries) {
            results.add(new PsiElementResolveResult(dictionary));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        Project project = myElement.getProject();
        List<UdlDictionary> dictionaries = UdlUtil.findDictionaries(project);
        List<LookupElement> variants = new ArrayList<>();
        for (final UdlDictionary dictionary : dictionaries) {
            if (dictionary.getName() != null && !dictionary.getName().isEmpty()) {
                variants.add(LookupElementBuilder.create(dictionary)
                        .withIcon(UdlIcons.FILE)
                        .withTypeText(dictionary.getContainingFile().getName()));
            }
        }
        return variants.toArray();
    }
}
