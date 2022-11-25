package dev.lonami.uniffidl;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import dev.lonami.uniffidl.psi.UdlDictionary;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UdlDocumentationProvider extends AbstractDocumentationProvider {
    @Override
    public @Nullable @Nls String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (element instanceof UdlDictionary) {
            UdlDictionary dictionary = (UdlDictionary) element;
            final String name = dictionary.getIdentifier().getText();
            final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
            final String docComment = UdlUtil.findDocumentationComment(dictionary);
            return renderFullDoc(name, file, docComment);

        }
        return null;
    }

    @Override
    public @Nullable @Nls String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
        return generateDoc(element, originalElement);
    }

    @Override
    public @Nullable @Nls String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof UdlDictionary) {
            UdlDictionary dictionary = (UdlDictionary) element;
            final String name = dictionary.getIdentifier().getText();
            final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
            return "'" + name + "' in " + file;
        }
        return null;
    }

    private String renderFullDoc(String name, String file, String docComment) {
        StringBuilder sb = new StringBuilder();
        sb.append(DocumentationMarkup.DEFINITION_START);
        sb.append("Dictionary");
        sb.append(DocumentationMarkup.DEFINITION_END);
        sb.append(DocumentationMarkup.CONTENT_START);
        sb.append(name);
        sb.append(DocumentationMarkup.CONTENT_END);
        sb.append(DocumentationMarkup.SECTIONS_START);
        addKeyValueSection("Name:", name, sb);
        addKeyValueSection("File:", file, sb);
        addKeyValueSection("Comment:", docComment, sb);
        sb.append(DocumentationMarkup.SECTIONS_END);
        return sb.toString();
    }

    private void addKeyValueSection(String key, String value, StringBuilder sb) {
        sb.append(DocumentationMarkup.SECTION_HEADER_START);
        sb.append(key);
        sb.append(DocumentationMarkup.SECTION_SEPARATOR);
        sb.append("<p>");
        sb.append(value);
        sb.append(DocumentationMarkup.SECTION_END);
    }
}
