package dev.lonami.uniffidl

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import dev.lonami.uniffidl.psi.UdlDefinition

class UdlChooseByNameContributor : ChooseByNameContributor {
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        val definitions = UdlUtil.findTypeDefinitions(project)
        val names = mutableListOf<String>()
        for (definition in definitions) {
            val name = definition.name
            if (name != null && name.isNotEmpty()) {
                names.add(name)
            }
        }
        return names.toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        val definitions = UdlUtil.findTypeDefinitions(project, name)
        return definitions.toTypedArray()
    }
}
