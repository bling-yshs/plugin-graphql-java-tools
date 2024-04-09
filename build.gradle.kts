import org.jetbrains.changelog.markdownToHTML

fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.16.0"
    id("org.jetbrains.changelog") version "2.2.0"
}

group = "com.onepiece.wj"
version = properties("plugin.version")

repositories {
    maven(url = "https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.3")
    type.set("IU") // Target IDE Platform
    downloadSources.set(true)
    // Read this: https://blog.jetbrains.com/platform/2019/06/java-functionality-extracted-as-a-plugin/
    plugins.set(listOf("com.intellij.java", "com.intellij.lang.jsgraphql:4.0.2"))
}

changelog {
//    groups.empty()
    repositoryUrl = properties("plugin.repository.url")
    version = properties("plugin.version")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("241.*")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription = """
            <![CDATA[
            ${extractPluginDesc("README.md")}
            
            ${extractPluginDesc("README_zh.md")}
            ]]>
        """.trimIndent()
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

fun extractPluginDesc(fileName: String) =
    providers.fileContents(layout.projectDirectory.file(fileName)).asText.map {
        val start = "<!-- Plugin description -->"
        val end = "<!-- Plugin description end -->"

        with(it.lines()) {
            if (!containsAll(listOf(start, end))) {
                throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
            }
            subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
        }
    }.get()
