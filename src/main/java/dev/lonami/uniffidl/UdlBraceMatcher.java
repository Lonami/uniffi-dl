package dev.lonami.uniffidl;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.psi.UdlTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UdlBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            // TODO angle brackets don't seem to work, see the following link for details:
            //      https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000393430-Auto-closing-braces-in-a-custom-language-BraceMatcher-not-working
            new BracePair(UdlTypes.OP_OPEN_ANGLE_BRACKET, UdlTypes.OP_CLOSE_ANGLE_BRACKET, false),
            new BracePair(UdlTypes.OP_OPEN_PAREN, UdlTypes.OP_CLOSE_PAREN, false),
            new BracePair(UdlTypes.OP_OPEN_BRACKET, UdlTypes.OP_CLOSE_BRACKET, false),
            new BracePair(UdlTypes.OP_OPEN_BRACE, UdlTypes.OP_CLOSE_BRACE, true),
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
