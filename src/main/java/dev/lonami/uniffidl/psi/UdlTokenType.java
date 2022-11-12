package dev.lonami.uniffidl.psi;

import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.UdlLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class UdlTokenType extends IElementType {
    public UdlTokenType(@NonNls @NotNull String debugName) {
        super(debugName, UdlLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "UdlTokenType." + super.toString();
    }
}
