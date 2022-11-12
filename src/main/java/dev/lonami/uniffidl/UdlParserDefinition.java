package dev.lonami.uniffidl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import dev.lonami.uniffidl.parser.UdlParser;
import dev.lonami.uniffidl.psi.UdlFile;
import dev.lonami.uniffidl.psi.UdlTokenSets;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;

public class UdlParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(UdlLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new UdlLexerAdapter();
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return UdlTokenSets.COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return UdlTokenSets.STRINGS;
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new UdlParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new UdlFile(viewProvider);
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return UdlTypes.Factory.createElement(node);
    }
}
