package dev.lonami.uniffidl;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;

public class UdlCompletionContributor extends CompletionContributor {
    public UdlCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(UdlTypes.IDENTIFIER), new CompletionProvider<>() {
            @Override
            protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                result.addElement(LookupElementBuilder.create("namespace"));
                result.addElement(LookupElementBuilder.create("dictionary"));
                result.addElement(LookupElementBuilder.create("interface"));
            }
        });
    }
}
