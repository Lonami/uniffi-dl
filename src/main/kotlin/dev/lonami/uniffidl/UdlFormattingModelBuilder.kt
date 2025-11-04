package dev.lonami.uniffidl

import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import dev.lonami.uniffidl.psi.UdlTypes

class UdlFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            UdlBlock(
                formattingContext.node,
                Wrap.createWrap(WrapType.NONE, false),
                createSpaceBuilder(codeStyleSettings)
            ),
            codeStyleSettings
        )
    }

    companion object {
        private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
            val languageId = UdlLanguage.INSTANCE.id
            return SpacingBuilder(settings, UdlLanguage.INSTANCE)
                .between(UdlTypes.IDENTIFIER, UdlTypes.OP_OPEN_BRACE)
                .spaceIf(settings.getCommonSettings(languageId).SPACE_BEFORE_CLASS_LBRACE)
                .between(UdlTypes.IDENTIFIER, UdlTypes.OP_OPEN_PAREN)
                .spaceIf(settings.getCommonSettings(languageId).SPACE_BEFORE_METHOD_PARENTHESES)
                .before(UdlTypes.DEFINITION)
                .none()
        }
    }
}
