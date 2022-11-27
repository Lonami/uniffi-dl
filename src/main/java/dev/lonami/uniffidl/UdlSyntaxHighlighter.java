package dev.lonami.uniffidl;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class UdlSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("UDL_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("UDL_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("UDL_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = createTextAttributesKey("UDL_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("UDL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BRACES = createTextAttributesKey("UDL_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey DOT = createTextAttributesKey("UDL_DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("UDL_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA = createTextAttributesKey("UDL_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("UDL_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("UDL_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);

    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] DOT_KEYS = new TextAttributesKey[]{DOT};
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{SEMICOLON};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new UdlLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(UdlTypes.IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        }
        if (tokenType.equals(UdlTypes.KW_NEGINF)
                | tokenType.equals(UdlTypes.KW_POSINF)
                | tokenType.equals(UdlTypes.KW_NAN)
                | tokenType.equals(UdlTypes.INTEGER)
                | tokenType.equals(UdlTypes.DECIMAL)) {
            return NUMBER_KEYS;
        }
        if (tokenType.equals(UdlTypes.KW_ANY)
                | tokenType.equals(UdlTypes.KW_ASYNC)
                | tokenType.equals(UdlTypes.KW_ATTRIBUTE)
                | tokenType.equals(UdlTypes.KW_BOOLEAN)
                | tokenType.equals(UdlTypes.KW_CALLBACK)
                | tokenType.equals(UdlTypes.KW_CONST)
                | tokenType.equals(UdlTypes.KW_CONSTRUCTOR)
                | tokenType.equals(UdlTypes.KW_DELETER)
                | tokenType.equals(UdlTypes.KW_DICTIONARY)
                | tokenType.equals(UdlTypes.KW_DOUBLE)
                | tokenType.equals(UdlTypes.KW_DURATION)
                | tokenType.equals(UdlTypes.KW_ENUM)
                | tokenType.equals(UdlTypes.KW_FALSE)
                | tokenType.equals(UdlTypes.KW_FLOAT)
                | tokenType.equals(UdlTypes.KW_GETTER)
                | tokenType.equals(UdlTypes.KW_I8)
                | tokenType.equals(UdlTypes.KW_I16)
                | tokenType.equals(UdlTypes.KW_I32)
                | tokenType.equals(UdlTypes.KW_I64)
                | tokenType.equals(UdlTypes.KW_INCLUDES)
                | tokenType.equals(UdlTypes.KW_INHERIT)
                | tokenType.equals(UdlTypes.KW_INTERFACE)
                | tokenType.equals(UdlTypes.KW_ITERABLE)
                | tokenType.equals(UdlTypes.KW_MAPLIKE)
                | tokenType.equals(UdlTypes.KW_MIXIN)
                | tokenType.equals(UdlTypes.KW_NAMESPACE)
                | tokenType.equals(UdlTypes.KW_NULL)
                | tokenType.equals(UdlTypes.KW_OBJECT)
                | tokenType.equals(UdlTypes.KW_OPTIONAL)
                | tokenType.equals(UdlTypes.KW_OR)
                | tokenType.equals(UdlTypes.KW_PARTIAL)
                | tokenType.equals(UdlTypes.KW_READONLY)
                | tokenType.equals(UdlTypes.KW_RECORD)
                | tokenType.equals(UdlTypes.KW_REQUIRED)
                | tokenType.equals(UdlTypes.KW_SEQUENCE)
                | tokenType.equals(UdlTypes.KW_SETLIKE)
                | tokenType.equals(UdlTypes.KW_SETTER)
                | tokenType.equals(UdlTypes.KW_STATIC)
                | tokenType.equals(UdlTypes.KW_STRING)
                | tokenType.equals(UdlTypes.KW_STRINGIFIER)
                | tokenType.equals(UdlTypes.KW_SYMBOL)
                | tokenType.equals(UdlTypes.KW_TIMESTAMP)
                | tokenType.equals(UdlTypes.KW_TRUE)
                | tokenType.equals(UdlTypes.KW_TYPEDEF)
                | tokenType.equals(UdlTypes.KW_U8)
                | tokenType.equals(UdlTypes.KW_U16)
                | tokenType.equals(UdlTypes.KW_U32)
                | tokenType.equals(UdlTypes.KW_U64)
                | tokenType.equals(UdlTypes.KW_UNDEFINED)
                | tokenType.equals(UdlTypes.KW_UNRESTRICTED)
                | tokenType.equals(UdlTypes.KW_VOID)) {
            return KEYWORD_KEYS;
        }
        if (tokenType.equals(UdlTypes.STRING)) {
            return STRING_KEYS;
        }
        if (tokenType.equals(UdlTypes.COMMENT)) {
            return COMMENT_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_OPEN_BRACE)
                | tokenType.equals(UdlTypes.OP_CLOSE_BRACE)) {
            return BRACES_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_DOT)
                | tokenType.equals(UdlTypes.OP_COLON)
                | tokenType.equals(UdlTypes.OP_ELLIPSIS)) {
            return DOT_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_SEMICOLON)) {
            return SEMICOLON_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_SEPARATOR)) {
            return COMMA_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_OPEN_PAREN)
                | tokenType.equals(UdlTypes.OP_CLOSE_PAREN)) {
            return PARENTHESES_KEYS;
        }
        if (tokenType.equals(UdlTypes.OP_OPEN_BRACKET)
                | tokenType.equals(UdlTypes.OP_CLOSE_BRACKET)) {
            return BRACKETS_KEYS;
        }

        return EMPTY_KEYS;
    }
}
