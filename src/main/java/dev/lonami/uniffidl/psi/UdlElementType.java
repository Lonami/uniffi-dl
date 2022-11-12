package dev.lonami.uniffidl.psi;

import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.UdlLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class UdlElementType extends IElementType {
    public  UdlElementType(@NonNls @NotNull String debugName) {
        super(debugName, UdlLanguage.INSTANCE);
    }
}
