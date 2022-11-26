package dev.lonami.uniffidl;

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import dev.lonami.uniffidl.psi.UdlDefinition;
import dev.lonami.uniffidl.psi.UdlFile;
import dev.lonami.uniffidl.psi.impl.UdlPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UdlStructureAwareNavbar extends StructureAwareNavBarModelExtension {
    @NotNull
    @Override
    protected Language getLanguage() {
        return UdlLanguage.INSTANCE;
    }

    @Override
    public @Nullable String getPresentableText(Object object) {
        if (object instanceof UdlFile) {
            return ((UdlFile) object).getName();
        }
        if (object instanceof UdlDefinition) {
            return ((UdlDefinition) object).getName();
        }

        return null;
    }

    @Override
    public @Nullable Icon getIcon(Object object) {
        if (object instanceof UdlDefinition) {
            return UdlPsiImplUtil.getPresentation((UdlDefinition) object).getIcon(false);
        }

        return null;
    }
}
