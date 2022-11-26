package dev.lonami.uniffidl;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class UdlIcons {
    public static final Icon FILE = IconLoader.getIcon("/icons/udl.png", UdlIcons.class);
    public static final Icon TYPE_DEF = AllIcons.Nodes.Type;
    public static final Icon DICTIONARY = AllIcons.Nodes.Class;
    public static final Icon ENUM = AllIcons.Nodes.Enum;
    public static final Icon NAMESPACE = AllIcons.Nodes.Artifact;
    public static final Icon PARTIAL = AllIcons.Nodes.AbstractClass;
    public static final Icon INTERFACE = AllIcons.Nodes.Interface;
    public static final Icon CALLBACK = AllIcons.Nodes.MethodReference;
}
