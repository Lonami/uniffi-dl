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
  {WHITESPACE}       { return UdlTypes.WHITESPACE; }
  {COMMENT}          { return UdlTypes.COMMENT; }

  {INTEGER}          { return UdlTypes.INTEGER; }
  {DECIMAL}          { return UdlTypes.DECIMAL; }
  {IDENTIFIER}       { return UdlTypes.IDENTIFIER; }
  {STRING}           { return UdlTypes.STRING; }
  {ALT}              { return UdlTypes.ALT; }
}
