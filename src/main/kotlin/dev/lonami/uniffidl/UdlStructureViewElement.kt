package dev.lonami.uniffidl

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import dev.lonami.uniffidl.psi.UdlDefinition
import dev.lonami.uniffidl.psi.UdlFile
import dev.lonami.uniffidl.psi.impl.UdlDefinitionImpl

class UdlStructureViewElement(private val myElement: NavigatablePsiElement) :
    StructureViewTreeElement, SortableTreeElement {

    override fun getValue(): Any = myElement

    override fun navigate(requestFocus: Boolean) {
        myElement.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = myElement.canNavigate()

    override fun canNavigateToSource(): Boolean = myElement.canNavigateToSource()

    override fun getAlphaSortKey(): String {
        val name = myElement.name
        return name ?: ""
    }

    override fun getPresentation(): ItemPresentation {
        val presentation = myElement.presentation
        return presentation ?: PresentationData()
    }

    override fun getChildren(): Array<TreeElement> {
        return when (myElement) {
            is UdlFile -> {
                val definitions = UdlUtil.findDefinitions(myElement)
                val treeElements = mutableListOf<TreeElement>()
                for (definition in definitions) {
                    treeElements.add(UdlStructureViewElement(definition as UdlDefinitionImpl))
                }
                treeElements.toTypedArray()
            }
            is UdlDefinition -> {
                val children = UdlUtil.findDefinitionChildren(myElement)
                val treeElements = mutableListOf<TreeElement>()
                for (child in children) {
                    treeElements.add(UdlStructureViewElement(child))
                }
                treeElements.toTypedArray()
            }
            else -> EMPTY_ARRAY
        }
    }
}
