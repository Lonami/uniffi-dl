package dev.lonami.uniffidl

import com.intellij.lang.Language

class UdlLanguage private constructor() : Language("Uniffi-DL") {
    companion object {
        @JvmStatic
        val INSTANCE = UdlLanguage()
    }
}
