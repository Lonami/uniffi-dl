package dev.lonami.uniffidl;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.psi.UdlTypes;
import com.intellij.psi.TokenType;

%%

%class UdlLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

INTEGER=-?([1-9][0-9]*|0[Xx][0-9A-Fa-f]+|0[0-7]*)
DECIMAL=-?(([0-9]+\.[0-9]*|[0-9]*\.[0-9]+)([Ee][+-]?[0-9]+)?|[0-9]+[Ee][+-]?[0-9]+)
IDENTIFIER=[_-]?[A-Za-z][0-9A-Z_a-z-]*
STRING=\"[^\"]*\"
WHITESPACE=[\t\n\r ]+
COMMENT="//".*|"/"\*[^]*?\*"/"
ALT=[^\t\n\r 0-9A-Za-z]

%%

<YYINITIAL> {
  {COMMENT}           { return UdlTypes.COMMENT; }
  {WHITESPACE}        { return TokenType.WHITE_SPACE; }

  "("                 { return UdlTypes.OP_OPEN_PAREN; }
  ")"                 { return UdlTypes.OP_CLOSE_PAREN; }
  "*"                 { return UdlTypes.OP_WILDCARD; }
  ","                 { return UdlTypes.OP_SEPARATOR; }
  "-"                 { return UdlTypes.OP_NEG; }
  "-Infinity"         { return UdlTypes.KW_NEGINF; }
  "."                 { return UdlTypes.OP_DOT; }
  "..."               { return UdlTypes.OP_ELLIPSIS; }
  ":"                 { return UdlTypes.OP_COLON; }
  ";"                 { return UdlTypes.OP_SEMICOLON; }
  "<"                 { return UdlTypes.OP_OPEN_ANGLE_BRACKET; }
  "="                 { return UdlTypes.OP_EQUALS; }
  ">"                 { return UdlTypes.OP_CLOSE_ANGLE_BRACKET; }
  "?"                 { return UdlTypes.OP_NULLABLE; }
  "Infinity"          { return UdlTypes.KW_POSINF; }
  "NaN"               { return UdlTypes.KW_NAN; }
  "["                 { return UdlTypes.OP_OPEN_BRACKET; }
  "]"                 { return UdlTypes.OP_CLOSE_BRACKET; }
  "any"               { return UdlTypes.KW_ANY; }
  "async"             { return UdlTypes.KW_ASYNC; }
  "attribute"         { return UdlTypes.KW_ATTRIBUTE; }
  "boolean"           { return UdlTypes.KW_BOOLEAN; }
  "callback"          { return UdlTypes.KW_CALLBACK; }
  "const"             { return UdlTypes.KW_CONST; }
  "constructor"       { return UdlTypes.KW_CONSTRUCTOR; }
  "deleter"           { return UdlTypes.KW_DELETER; }
  "dictionary"        { return UdlTypes.KW_DICTIONARY; }
  "double"            { return UdlTypes.KW_DOUBLE; }
  "duration"          { return UdlTypes.KW_DURATION; }
  "enum"              { return UdlTypes.KW_ENUM; }
  "false"             { return UdlTypes.KW_FALSE; }
  "float"             { return UdlTypes.KW_FLOAT; }
  "getter"            { return UdlTypes.KW_GETTER; }
  "i8"                { return UdlTypes.KW_I8; }
  "i16"               { return UdlTypes.KW_I16; }
  "i32"               { return UdlTypes.KW_I32; }
  "i64"               { return UdlTypes.KW_I64; }
  "includes"          { return UdlTypes.KW_INCLUDES; }
  "inherit"           { return UdlTypes.KW_INHERIT; }
  "interface"         { return UdlTypes.KW_INTERFACE; }
  "iterable"          { return UdlTypes.KW_ITERABLE; }
  "maplike"           { return UdlTypes.KW_MAPLIKE; }
  "mixin"             { return UdlTypes.KW_MIXIN; }
  "namespace"         { return UdlTypes.KW_NAMESPACE; }
  "null"              { return UdlTypes.KW_NULL; }
  "object"            { return UdlTypes.KW_OBJECT; }
  "optional"          { return UdlTypes.KW_OPTIONAL; }
  "or"                { return UdlTypes.KW_OR; }
  "partial"           { return UdlTypes.KW_PARTIAL; }
  "readonly"          { return UdlTypes.KW_READONLY; }
  "record"            { return UdlTypes.KW_RECORD; }
  "required"          { return UdlTypes.KW_REQUIRED; }
  "sequence"          { return UdlTypes.KW_SEQUENCE; }
  "setlike"           { return UdlTypes.KW_SETLIKE; }
  "setter"            { return UdlTypes.KW_SETTER; }
  "static"            { return UdlTypes.KW_STATIC; }
  "string"            { return UdlTypes.KW_STRING; }
  "stringifier"       { return UdlTypes.KW_STRINGIFIER; }
  "symbol"            { return UdlTypes.KW_SYMBOL; }
  "timestamp"         { return UdlTypes.KW_TIMESTAMP; }
  "true"              { return UdlTypes.KW_TRUE; }
  "typedef"           { return UdlTypes.KW_TYPEDEF; }
  "u8"                { return UdlTypes.KW_U8; }
  "u16"               { return UdlTypes.KW_U16; }
  "u32"               { return UdlTypes.KW_U32; }
  "u64"               { return UdlTypes.KW_U64; }
  "undefined"         { return UdlTypes.KW_UNDEFINED; }
  "unrestricted"      { return UdlTypes.KW_UNRESTRICTED; }
  "void"              { return UdlTypes.KW_VOID; }
  "{"                 { return UdlTypes.OP_OPEN_BRACE; }
  "|"                 { return UdlTypes.OP_PIPE; }
  "}"                 { return UdlTypes.OP_CLOSE_BRACE; }
  "KW_LONG"           { return UdlTypes.KW_LONG; }
  "KW_SHORT"          { return UdlTypes.KW_SHORT; }
  "KW_UNSIGNED"       { return UdlTypes.KW_UNSIGNED; }

  {INTEGER}           { return UdlTypes.INTEGER; }
  {DECIMAL}           { return UdlTypes.DECIMAL; }
  {IDENTIFIER}        { return UdlTypes.IDENTIFIER; }
  {STRING}            { return UdlTypes.STRING; }
  {WHITESPACE}        { return UdlTypes.WHITESPACE; }
  {COMMENT}           { return UdlTypes.COMMENT; }
  {ALT}               { return UdlTypes.ALT; }

}
