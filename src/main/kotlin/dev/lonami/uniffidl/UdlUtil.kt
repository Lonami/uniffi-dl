package dev.lonami.uniffidl

import com.intellij.navigation.NavigatablePsiElement
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import dev.lonami.uniffidl.psi.*
import java.util.function.Predicate

object UdlUtil {
    private fun isTypeDefinition(definition: UdlDefinition): Boolean {
        return definition.typedef != null || definition.dictionary != null || definition.enum != null
    }

    @JvmStatic
    fun getDefinitionTypeText(definition: UdlDefinition): String {
        return definition.firstChild.firstChild.text
    }

    /**
     * Search the entire project for type definitions (typedef, dictionary or enum) matching the given key.
     *
     * @param project haystack
     * @param key     needle
     * @return matching definitions
     */
    @JvmStatic
    fun findTypeDefinitions(project: Project, key: String): List<UdlDefinition> {
        return findDefinitions(project) { definition ->
            isTypeDefinition(definition) && key == definition.name
        }
    }

    @JvmStatic
    fun findTypeDefinitions(project: Project): List<UdlDefinition> {
        return findDefinitions(project, ::isTypeDefinition)
    }

    @JvmStatic
    fun findDefinitions(udlFile: UdlFile): List<UdlDefinition> {
        return findDefinitions(udlFile, null, null)
    }

    private fun findDefinitions(project: Project, filter: Predicate<UdlDefinition>?): List<UdlDefinition> {
        var result = mutableListOf<UdlDefinition>()

        val virtualFiles: Collection<VirtualFile> = FileTypeIndex.getFiles(
            UdlFileType.INSTANCE,
            GlobalSearchScope.allScope(project)
        )

        for (virtualFile in virtualFiles) {
            val udlFile = PsiManager.getInstance(project).findFile(virtualFile) as? UdlFile
            result = findDefinitions(udlFile, result, filter).toMutableList()
        }

        return result
    }

    private fun findDefinitions(
        udlFile: UdlFile?,
        resultParam: MutableList<UdlDefinition>?,
        filter: Predicate<UdlDefinition>?
    ): List<UdlDefinition> {
        val result = resultParam ?: mutableListOf()

        if (udlFile != null) {
            val definitions = PsiTreeUtil.getChildrenOfType(udlFile, UdlDefinition::class.java)
            if (definitions != null) {
                for (definition in definitions) {
                    if (filter == null || filter.test(definition)) {
                        result.add(definition)
                    }
                }
            }
        }

        return result
    }

    @JvmStatic
    fun findDefinitionChildren(definition: UdlDefinition): List<NavigatablePsiElement> {
        val result = mutableListOf<NavigatablePsiElement>()

        val members = PsiTreeUtil.findChildrenOfType(definition, UdlDictionaryMember::class.java)
        for (member in members) {
            result.add(member as NavigatablePsiElement)
        }

        val operations = PsiTreeUtil.findChildrenOfType(definition, UdlRegularOperation::class.java)
        for (operation in operations) {
            result.add(operation as NavigatablePsiElement)
        }

        val constructors = PsiTreeUtil.findChildrenOfType(definition, UdlConstructor::class.java)
        for (constructor in constructors) {
            result.add(constructor as NavigatablePsiElement)
        }

        return result
    }

    /**
     * Attempt to collect any comment elements above the definition into a string.
     *
     * @param definition point before which to search for comments
     * @return comments' contents
     */
    @JvmStatic
    fun findDocumentationComment(definition: UdlDefinition): String {
        val result = mutableListOf<String>()

        var element: PsiElement? = definition.prevSibling?.prevSibling
        while (element is PsiComment || element is PsiWhiteSpace) {
            if (element is PsiComment) {
                var commentText = element.text.replaceFirst(Regex("/[/*]+"), "")
                var len = commentText.length
                if (len >= 2 && commentText[len - 1] == '/' && commentText[len - 2] == '*') {
                    len -= 2
                    while (commentText[len] == '*') {
                        len -= 1
                    }
                    commentText = commentText.substring(0, len)
                }
                result.add(commentText)
            }
            element = element.prevSibling
        }

        return StringUtil.join(result.reversed(), "\n ")
    }
}
