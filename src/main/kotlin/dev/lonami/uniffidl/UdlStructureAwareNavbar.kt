package dev.lonami.uniffidl

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension
import com.intellij.lang.Language
import dev.lonami.uniffidl.psi.UdlDefinition
import dev.lonami.uniffidl.psi.UdlFile
import dev.lonami.uniffidl.psi.impl.UdlPsiImplUtil
import javax.swing.Icon

class UdlStructureAwareNavbar : StructureAwareNavBarModelExtension() {
    override fun getLanguage(): Language = UdlLanguage.INSTANCE

    override fun getPresentableText(`object`: Any?): String? {
        return when (`object`) {
            is UdlFile -> `object`.name
            is UdlDefinition -> `object`.name
            else -> null
        }
    }

    override fun getIcon(`object`: Any?): Icon? {
        if (`object` is UdlDefinition) {
            return UdlPsiImplUtil.getPresentation(`object`).getIcon(false)
        }
        return null
    }
}
