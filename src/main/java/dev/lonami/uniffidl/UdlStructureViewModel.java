package dev.lonami.uniffidl;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import dev.lonami.uniffidl.psi.UdlDictionary;
import dev.lonami.uniffidl.psi.UdlIdentifiers;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class UdlStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
    public UdlStructureViewModel(@Nullable Editor editor, PsiFile psiFile) {
        super(psiFile, editor, new UdlStructureViewElement(psiFile));
    }

    @Override
    public Sorter @NotNull [] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }

    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element.getValue() instanceof UdlDictionary;
    }

    @Override
    protected Class<?> @NotNull [] getSuitableClasses() {
        return new Class[]{
                UdlDictionary.class
        };
    }
}
