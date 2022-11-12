## Building

Right-click `src/main/kotlin/dev/lonami/uniffidl/Udl.bnf` and select "Generate Parser Code".

Right-click `src/main/kotlin/dev/lonami/uniffidl/Udl.flex` and select "Run JFlex Generator".
During the first run you will have to select the root of the project itself to download
both `idea-flex.skeleton` and `jflex*.jar`.

Then it should be possible to run `gradlew buildPlugin` or `gradlew runIde`.
