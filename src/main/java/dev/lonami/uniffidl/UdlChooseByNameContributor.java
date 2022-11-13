package dev.lonami.uniffidl;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import dev.lonami.uniffidl.psi.UdlDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UdlChooseByNameContributor implements ChooseByNameContributor {
    @Override
    public String @NotNull [] getNames(Project project, boolean includeNonProjectItems) {
        List<UdlDictionary> dictionaries = UdlUtil.findDictionaries(project);
        List<String> names = new ArrayList<>(dictionaries.size());
        for (UdlDictionary dictionary : dictionaries) {
            if (dictionary.getName() != null && !dictionary.getName().isEmpty()) {
                names.add(dictionary.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    @Override
    public NavigationItem @NotNull [] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<UdlDictionary> dictionaries = UdlUtil.findDictionaries(project, name);
        return dictionaries.toArray(new NavigationItem[0]);
    }
}
