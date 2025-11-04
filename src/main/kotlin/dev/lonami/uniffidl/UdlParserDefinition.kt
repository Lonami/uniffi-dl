package dev.lonami.uniffidl

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.lonami.uniffidl.parser.UdlParser
import dev.lonami.uniffidl.psi.UdlFile
import dev.lonami.uniffidl.psi.UdlTokenSets
import dev.lonami.uniffidl.psi.UdlTypes

class UdlParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer = UdlLexerAdapter()

    override fun getCommentTokens(): TokenSet = UdlTokenSets.COMMENTS

    override fun getStringLiteralElements(): TokenSet = UdlTokenSets.STRINGS

    override fun createParser(project: Project): PsiParser = UdlParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createFile(viewProvider: FileViewProvider): PsiFile = UdlFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = UdlTypes.Factory.createElement(node)

    companion object {
        @JvmField
        val FILE = IFileElementType(UdlLanguage.INSTANCE)
    }
}
