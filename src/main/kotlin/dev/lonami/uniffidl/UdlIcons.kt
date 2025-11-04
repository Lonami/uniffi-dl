package dev.lonami.uniffidl

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object UdlIcons {
    @JvmField
    val FILE: Icon = IconLoader.getIcon("/icons/udl.png", UdlIcons::class.java)
    @JvmField
    val TYPE_DEF: Icon = AllIcons.Nodes.Type
    @JvmField
    val DICTIONARY: Icon = AllIcons.Nodes.Class
    @JvmField
    val ENUM: Icon = AllIcons.Nodes.Enum
    @JvmField
    val NAMESPACE: Icon = AllIcons.Nodes.Artifact
    @JvmField
    val PARTIAL: Icon = AllIcons.Nodes.AbstractClass
    @JvmField
    val INTERFACE: Icon = AllIcons.Nodes.Interface
    @JvmField
    val CALLBACK: Icon = AllIcons.Nodes.Lambda
    @JvmField
    val CONSTRUCTOR: Icon = AllIcons.Nodes.ClassInitializer
    @JvmField
    val METHOD: Icon = AllIcons.Nodes.Method
    @JvmField
    val PROPERTY: Icon = AllIcons.Nodes.Property
}
