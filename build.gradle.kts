plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

group = "dev.lonami"
version = "0.3.3"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        val type = org.jetbrains.intellij.platform.gradle.IntelliJPlatformType.IntellijIdeaCommunity
        val version = providers.gradleProperty("ideaVersion")
        create(type, version)

        instrumentationTools()
        // plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })
        // bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
intellijPlatform {
    pluginConfiguration {
        version = "${rootProject.version}"
        ideaVersion {
            sinceBuild.set(providers.gradleProperty("sinceBuild"))
            untilBuild.set(providers.gradleProperty("untilBuild"))
        }
    }
    signing {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }
    publishing {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
    buildSearchableOptions  = false
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}

sourceSets["main"].java.srcDirs("src/main/gen")
