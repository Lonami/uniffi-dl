package dev.lonami.uniffidl

import com.intellij.lexer.FlexAdapter

class UdlLexerAdapter : FlexAdapter(UdlLexer(null))
