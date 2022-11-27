package dev.lonami.uniffidl;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;

public class UdlFormattingModelBuilder implements FormattingModelBuilder {
    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, UdlLanguage.INSTANCE)
                .between(UdlTypes.IDENTIFIER, UdlTypes.OP_OPEN_BRACE)
                .spaceIf(settings.getCommonSettings(UdlLanguage.INSTANCE.getID()).SPACE_BEFORE_CLASS_LBRACE)
                .between(UdlTypes.IDENTIFIER, UdlTypes.OP_OPEN_PAREN)
                .spaceIf(settings.getCommonSettings(UdlLanguage.INSTANCE.getID()).SPACE_BEFORE_METHOD_PARENTHESES)
                .before(UdlTypes.DEFINITION)
                .none();
    }

    @Override
    public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
        final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
        return FormattingModelProvider.createFormattingModelForPsiFile(formattingContext.getContainingFile(),
                new UdlBlock(formattingContext.getNode(),
                        Wrap.createWrap(WrapType.NONE, false),
                        Alignment.createAlignment(),
                        createSpaceBuilder(codeStyleSettings)),
                codeStyleSettings);
    }
}
