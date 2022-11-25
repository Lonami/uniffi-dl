package dev.lonami.uniffidl;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import dev.lonami.uniffidl.psi.UdlDictionary;
import dev.lonami.uniffidl.psi.UdlElementFactory;
import dev.lonami.uniffidl.psi.UdlFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public class UdlCreateDictionaryQuickFix extends BaseIntentionAction {
    private final String name;

    UdlCreateDictionaryQuickFix(String name) {
        this.name = name;
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return "Create dictionary '" + name + "'";
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "Create dictionary";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(() -> {
            Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(UdlFileType.INSTANCE, GlobalSearchScope.allScope(project));
            if (virtualFiles.size() == 1) {
                createDictionary(project, virtualFiles.iterator().next());
            } else {
                final FileChooserDescriptor descriptor =
                        FileChooserDescriptorFactory.createSingleFileDescriptor(UdlFileType.INSTANCE);
                descriptor.setRoots(ProjectUtil.guessProjectDir(project));
                final VirtualFile selectedFile = FileChooser.chooseFile(descriptor, project, null);
                if (selectedFile != null) {
                    createDictionary(project, selectedFile);
                }
            }
        });
    }

    private void createDictionary(final Project project, final VirtualFile file) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            UdlFile udlFile = Objects.requireNonNull((UdlFile) PsiManager.getInstance(project).findFile(file));
            ASTNode lastChildNode = udlFile.getNode().getLastChildNode();
            if (lastChildNode != null) {
                udlFile.getNode().addChild(UdlElementFactory.createLf(project).getNode());
            }
            UdlDictionary dictionary = UdlElementFactory.createDictionary(project, name);
            udlFile.getNode().addChild(dictionary.getNode());
            ((Navigatable) dictionary.getLastChild().getNavigationElement()).navigate(true);
            Objects.requireNonNull(FileEditorManager.getInstance(project).getSelectedTextEditor())
                    .getCaretModel().moveCaretRelatively(11, 0, false, false, false);
        });
    }
}
