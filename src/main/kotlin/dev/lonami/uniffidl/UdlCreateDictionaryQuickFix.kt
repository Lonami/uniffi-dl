package dev.lonami.uniffidl

import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.IncorrectOperationException
import dev.lonami.uniffidl.psi.UdlElementFactory
import dev.lonami.uniffidl.psi.UdlFile

class UdlCreateDictionaryQuickFix(private val name: String) : BaseIntentionAction() {

    override fun getText(): @IntentionName String = "Create dictionary '$name'"

    override fun getFamilyName(): @IntentionFamilyName String = "Create dictionary"

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean = true

    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        ApplicationManager.getApplication().invokeLater {
            val virtualFiles = FileTypeIndex.getFiles(UdlFileType.INSTANCE, GlobalSearchScope.allScope(project))
            when {
                virtualFiles.size == 1 -> {
                    createDictionary(project, virtualFiles.iterator().next())
                }
                else -> {
                    val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(UdlFileType.INSTANCE)
                    descriptor.setRoots(ProjectUtil.guessProjectDir(project))
                    val selectedFile = FileChooser.chooseFile(descriptor, project, null)
                    if (selectedFile != null) {
                        createDictionary(project, selectedFile)
                    }
                }
            }
        }
    }

    private fun createDictionary(project: Project, file: VirtualFile) {
        WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
            val udlFile = PsiManager.getInstance(project).findFile(file) as UdlFile
            val lastChildNode = udlFile.node.lastChildNode
            if (lastChildNode != null) {
                udlFile.node.addChild(UdlElementFactory.createLf(project)?.node)
            }
            val dictionary = UdlElementFactory.createDictionary(project, name)
            udlFile.node.addChild(dictionary?.node)
            (dictionary?.lastChild?.navigationElement as? Navigatable)?.navigate(true)
            FileEditorManager.getInstance(project).selectedTextEditor
                ?.caretModel?.moveCaretRelatively(11, 0, false, false, false)
        }
    }
}
