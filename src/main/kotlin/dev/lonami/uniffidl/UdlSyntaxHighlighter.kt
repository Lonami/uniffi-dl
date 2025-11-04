package dev.lonami.uniffidl

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import dev.lonami.uniffidl.psi.UdlTypes

class UdlSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = UdlLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when {
            tokenType == UdlTypes.IDENTIFIER -> IDENTIFIER_KEYS
            tokenType in NUMBER_TYPES -> NUMBER_KEYS
            tokenType in KEYWORD_TYPES -> KEYWORD_KEYS
            tokenType == UdlTypes.STRING -> STRING_KEYS
            tokenType == UdlTypes.COMMENT -> COMMENT_KEYS
            tokenType in BRACES_TYPES -> BRACES_KEYS
            tokenType in DOT_TYPES -> DOT_KEYS
            tokenType == UdlTypes.OP_SEMICOLON -> SEMICOLON_KEYS
            tokenType == UdlTypes.OP_SEPARATOR -> COMMA_KEYS
            tokenType in PARENTHESES_TYPES -> PARENTHESES_KEYS
            tokenType in BRACKETS_TYPES -> BRACKETS_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        @JvmField
        val IDENTIFIER = createTextAttributesKey("UDL_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        @JvmField
        val NUMBER = createTextAttributesKey("UDL_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        @JvmField
        val KEYWORD = createTextAttributesKey("UDL_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        @JvmField
        val STRING = createTextAttributesKey("UDL_STRING", DefaultLanguageHighlighterColors.STRING)
        @JvmField
        val COMMENT = createTextAttributesKey("UDL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        @JvmField
        val BRACES = createTextAttributesKey("UDL_BRACES", DefaultLanguageHighlighterColors.BRACES)
        @JvmField
        val DOT = createTextAttributesKey("UDL_DOT", DefaultLanguageHighlighterColors.DOT)
        @JvmField
        val SEMICOLON = createTextAttributesKey("UDL_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
        @JvmField
        val COMMA = createTextAttributesKey("UDL_COMMA", DefaultLanguageHighlighterColors.COMMA)
        @JvmField
        val PARENTHESES = createTextAttributesKey("UDL_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
        @JvmField
        val BRACKETS = createTextAttributesKey("UDL_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)

        private val IDENTIFIER_KEYS = arrayOf(IDENTIFIER)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val STRING_KEYS = arrayOf(STRING)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val BRACES_KEYS = arrayOf(BRACES)
        private val DOT_KEYS = arrayOf(DOT)
        private val SEMICOLON_KEYS = arrayOf(SEMICOLON)
        private val COMMA_KEYS = arrayOf(COMMA)
        private val PARENTHESES_KEYS = arrayOf(PARENTHESES)
        private val BRACKETS_KEYS = arrayOf(BRACKETS)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

        private val NUMBER_TYPES = setOf(
            UdlTypes.KW_NEGINF,
            UdlTypes.KW_POSINF,
            UdlTypes.KW_NAN,
            UdlTypes.INTEGER,
            UdlTypes.DECIMAL
        )

        private val KEYWORD_TYPES = setOf(
            UdlTypes.KW_ANY,
            UdlTypes.KW_ASYNC,
            UdlTypes.KW_ATTRIBUTE,
            UdlTypes.KW_BOOLEAN,
            UdlTypes.KW_CALLBACK,
            UdlTypes.KW_CONST,
            UdlTypes.KW_CONSTRUCTOR,
            UdlTypes.KW_DELETER,
            UdlTypes.KW_DICTIONARY,
            UdlTypes.KW_DOUBLE,
            UdlTypes.KW_DURATION,
            UdlTypes.KW_ENUM,
            UdlTypes.KW_FALSE,
            UdlTypes.KW_FLOAT,
            UdlTypes.KW_GETTER,
            UdlTypes.KW_I8,
            UdlTypes.KW_I16,
            UdlTypes.KW_I32,
            UdlTypes.KW_I64,
            UdlTypes.KW_INCLUDES,
            UdlTypes.KW_INHERIT,
            UdlTypes.KW_INTERFACE,
            UdlTypes.KW_ITERABLE,
            UdlTypes.KW_MAPLIKE,
            UdlTypes.KW_MIXIN,
            UdlTypes.KW_NAMESPACE,
            UdlTypes.KW_NULL,
            UdlTypes.KW_OBJECT,
            UdlTypes.KW_OPTIONAL,
            UdlTypes.KW_OR,
            UdlTypes.KW_PARTIAL,
            UdlTypes.KW_READONLY,
            UdlTypes.KW_RECORD,
            UdlTypes.KW_REQUIRED,
            UdlTypes.KW_SEQUENCE,
            UdlTypes.KW_SETLIKE,
            UdlTypes.KW_SETTER,
            UdlTypes.KW_STATIC,
            UdlTypes.KW_STRING,
            UdlTypes.KW_STRINGIFIER,
            UdlTypes.KW_SYMBOL,
            UdlTypes.KW_TIMESTAMP,
            UdlTypes.KW_TRUE,
            UdlTypes.KW_TYPEDEF,
            UdlTypes.KW_U8,
            UdlTypes.KW_U16,
            UdlTypes.KW_U32,
            UdlTypes.KW_U64,
            UdlTypes.KW_UNDEFINED,
            UdlTypes.KW_UNRESTRICTED,
            UdlTypes.KW_VOID
        )

        private val BRACES_TYPES = setOf(
            UdlTypes.OP_OPEN_BRACE,
            UdlTypes.OP_CLOSE_BRACE
        )

        private val DOT_TYPES = setOf(
            UdlTypes.OP_DOT,
            UdlTypes.OP_COLON,
            UdlTypes.OP_ELLIPSIS
        )

        private val PARENTHESES_TYPES = setOf(
            UdlTypes.OP_OPEN_PAREN,
            UdlTypes.OP_CLOSE_PAREN
        )

        private val BRACKETS_TYPES = setOf(
            UdlTypes.OP_OPEN_BRACKET,
            UdlTypes.OP_CLOSE_BRACKET
        )
    }
}
