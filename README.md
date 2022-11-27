# uniffi-dl

*uniffi-dl* is a plugin designed for the [IntelliJ IDE][idea] (and more precisely one of its other flavours,
namely [Android Studio][android-studio]) with the goal to provide syntax highlighting, code folding, code
completion, reference resolution and navigation, among other features such as offering view of the code
structure or handy quick fixes, for the [UniFFI Definition Language (UDL)][udl], a variant of [WebIDL][web-idl]
specifically tailored for the [UniFFI tool][uniffi] to "generate foreign-language bindings targeting Rust libraries".

[idea]: https://www.jetbrains.com/idea/

[android-studio]: https://developer.android.com/studio/

[udl]: https://mozilla.github.io/uniffi-rs/

[web-idl]: https://webidl.spec.whatwg.org/

[uniffi]: https://github.com/mozilla/uniffi-rs/

# FAQ

## Is this plugin the official plugin for UniFFI?

This is *not* the official plugin for the UniFFI tool (which doesn't exist as of 2022-11-27), but as far as I'm aware,
it's the first and only plugin to exist providing language support for `.udl` files.

If you're one of UniFFI's developers and would like to lift this plugin's status to be recognized as the official one,
please contact me.

## Why not a plugin for other code editors?

Chances are you'll be using UniFFI to embed Rust code in your Android applications (at least I am), in which case
you're most likely going to be using Android Studio, as it provides one of the better experiences (if not the best)
for developing Android applications.

If you port this plugin somewhere else and would like me to mention your plugin here, please contact me.

## My code styles settings are not being respected!

I personally have strong opinions in regard to the formatting rules, so I haven't really tried to make it
customizable. However, you're welcome to send a pull request which makes the formatting code obey the settings
(as long as there's a way for me to keep my old formatting style).

Not all formatting rules are currently implemented either, only some of the basic ones, so you're also welcome
implement these and send a pull request.

## This code is a mess! Why isn't it written in Kotlin?

Well, I tried, but I'm not all that good at porting Java concepts to Kotlin concepts, and the [Custom Language Support
Tutorial][cls-tutorial] is written in Java, so it was much less painful to follow along with Java. At the time of
writing, the tutorial also doesn't do that good of a job at *explaining* the way certain things work, but rather just
puts an order to when you're expected to implement certain features for the plugin, so for the most part, this plugin
is a snippet-by-snippet port from the Simple Language to UDL.

My goal with this was just to provide syntax highlighting for UDL and maybe even code completion, but I ended up
completing the entire tutorial to write a plugin, so it turned out to have more features than I was hoping for.

Please feel welcome to implement code improvements (such as de-duplication) and send a pull request, or perhaps even
a rewrite to Kotlin now that the core functionality works!

[cls-tutorial]: https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html

# Contributing

The first thing to do is to download and install [`git`][git], [IntelliJ IDEA][intellij], and the required plugins
for the IDE: *Plugin DevKit*, *Gradle* (both of which should be on by default), and additionally
[*Grammar-Kit*][grammar-kit] and [*PsiViewer*][psi-viewer] which can be downloaded from within the IDE.

[git]: https://www.git-scm.com/

[intellij]: https://www.jetbrains.com/idea/download/

[grammar-kit]: https://plugins.jetbrains.com/plugin/6606-grammar-kit

[psi-viewer]: https://plugins.jetbrains.com/plugin/227-psiviewer

## Building

Before building, the grammar must be used to generate the required types and parsing code.

Right-click `src/main/kotlin/dev/lonami/uniffidl/Udl.bnf` and select "Generate Parser Code".

Right-click `src/main/kotlin/dev/lonami/uniffidl/Udl.flex` and select "Run JFlex Generator".
During the first run you will have to select the root of the project itself to download
both `idea-flex.skeleton` and `jflex*.jar`.

(If you remove rules from the grammar you may need to delete the generated folder `gen`
before generating the code, otherwise, older, non-working rules may fail to compile.)

Then it should be possible to run `gradlew buildPlugin` or `gradlew runIde`, or by clicking on the "Play"
button of the IDE to run the selected Gradle configuration.

## Project Structure

Nearly every file, class or interface uses `Udl` as the prefix.

### Base Definitions

* The custom language singleton instance is defined in `UdlLanguage`.
* The `.udl` file type is associated with the language via `UdlFileType`.
* Icons such as the one used for the `.udl` files are in `UdlIcons`.

### Grammar

* Leaf grammar tokens are instances of `psi.UdlTokenType`, which are associated with the language.
* Intermediate grammar rules are instances of `psi.UdlElementType`, also linked to the language.
* The grammar description, mostly ported from WebIDL, is in the `Udl.bnf` file.
* The lexer description, consisting of a few core regular expressions and tokens, is in the `Udl.flex` file.
* `UdlLexerAdapter` is an auxiliary class for the IDE to be able to initialize the generated `UdlLexer`.
* `psi.UdlTokenSets` is used to define the groups a token belongs to.

### AST

* `psi.UdlFile` is the root of the parsed tree of `PsiElement`.
* `UdlParserDefinition` is what creates the parser, lexer, and root nodes for the language.
* The rest of nodes are autogenerated. Some of these contain additional, handwritten methods,
  which are defined in `psi.impl.UdlPsiImplUtil`, as referenced to in the `.bnf`. To provide
  other features such as resolving references and navigation, they must also implement
  `psi.UdlNamedElement` and `psi.impl.UdlNamedElementImpl`.
* `UdlUtil` contains several methods to make working with the AST easier.
* `psi.UdlElementFactory` simplifies the process of creating AST nodes outside of parsing.

### Features

* Folding of blocks is provided by `UdlFoldingBuilder`.
* Syntax highlighting based on the tokens is provided by `UdlSyntaxHighlighter`.
  The `UdlSyntaxHighlighterFactory` is necessary for the IDE to create an instance of the highlighter.
  A [custom Color Settings Page could be defined][color-settings] but is not currently implemented.
* Annotations are provided by the `UdlAnnotator`, which can provide more fine-tuned syntax highlighting
  based on the AST, as well as underlining errors and offering quick fixes. These quick, such as
  `UdlCreateDictionaryQuickFix`, are defined by implementing "intention actions".
* Line markers to help highlight the type of definitions via gutter icons and provide additional features
  such as quick navigation is currently not implemented, but a [custom Line Marker Provider][line-marker]
  could also be defined.
* Code completion is provided by `UdlCompletionContributor`. Brace matching is provided by `UdlBraceMatcher`.
* The commenter is defined by `UdlCommenter`, which simply defines the comment markers used by the language.
* Reference resolution is provided by `UdlReferenceContributor`, which returns instances of `UdlReference` in order
  to perform the actual resolution. [Support for in-place refactoring could be defined][inplace-refactor] but is not
  currently implemented.
* Find usages is provided by `UdlFindUsagesProvider`, while navigate to symbol is provided by
  `UdlChooseByNameContributor` for those elements which can `getPresentation`.
* Code structure is provided by `UdlStructureViewFactory`, which simply returns the `UdlStructureViewModel`.
  The view model returns an instance of `UdlStructureViewElement` with the root node of the AST. For the
  navigation bar to be aware of the structure, `UdlStructureAwareNavBar` is used instead.
* Automatic formatting is done by defining `UdlBlock` which is used to wrap the AST in a way that can be formatted.
  The `UdlFormattingModelBuilder` is used to create the first instance of the `UdlBlock` and define spacing rules.
  Similar to a color settings page, [a Code Style Settings page could be defined][code-settings], but is not currently
  implemented
* Both hover and quick documentation is provided by the `UdlDocumentationProvider`

[color-settings]: https://plugins.jetbrains.com/docs/intellij/syntax-highlighter-and-color-settings-page.html#define-a-color-settings-page

[line-marker]: https://plugins.jetbrains.com/docs/intellij/line-marker-provider.html#define-a-line-marker-provider

[inplace-refactor]: https://plugins.jetbrains.com/docs/intellij/reference-contributor.html#define-a-refactoring-support-provider

[code-settings]: https://plugins.jetbrains.com/docs/intellij/code-style-settings.html#define-code-style-settings
