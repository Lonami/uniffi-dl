package dev.lonami.uniffidl;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import dev.lonami.uniffidl.psi.UdlDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UdlChooseByNameContributor implements ChooseByNameContributor {
    @Override
    public String @NotNull [] getNames(Project project, boolean includeNonProjectItems) {
        List<UdlDefinition> definitions = UdlUtil.findTypeDefinitions(project);
        List<String> names = new ArrayList<>(definitions.size());
        for (UdlDefinition definition : definitions) {
            if (definition.getName() != null && !definition.getName().isEmpty()) {
                names.add(definition.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    @Override
    public NavigationItem @NotNull [] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<UdlDefinition> definitions = UdlUtil.findTypeDefinitions(project, name);
        return definitions.toArray(new NavigationItem[0]);
    }
}
