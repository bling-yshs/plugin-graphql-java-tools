import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.changelog.Changelog

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij.platform") version "2.9.0"
    id("org.jetbrains.changelog") version "2.2.0"
}

group = "com.onepiece.wj"
version = providers.gradleProperty("plugin.version").get()

repositories {
    maven(url = "https://maven.aliyun.com/repository/public/")
    mavenCentral()
    // ✅ 关键
    intellijPlatform { defaultRepositories() }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "232"
            untilBuild = provider { null } // 开放式兼容
        }

        // （可选）把 README 中的描述注入 plugin.xml
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"
            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        // （可选）从 changelog 生成 changeNotes
        val cl = project.changelog
        changeNotes = providers.gradleProperty("plugin.version").map { pv ->
            with(cl) {
                renderItem(
                    (getOrNull(pv) ?: getUnreleased()).withHeader(false).withEmptySections(false),
                    Changelog.OutputType.HTML
                )
            }
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }

    // （可选）IDE 兼容性验证：跑推荐矩阵
    pluginVerification {
        ides { recommended() }
    }
}

// 2.x 的依赖 DSL
dependencies {
    intellijPlatform {
        // 目标平台（开发/运行基线）
        create("IU", "2023.2.3")
        // 捆绑插件
        bundledPlugins("com.intellij.java")
        // 市场插件
        plugins("com.intellij.lang.jsgraphql:4.0.2")
        // （可选）工具
        instrumentationTools()
        pluginVerifier()
        zipSigner()
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

// ✅ 正确的位置：changelog 扩展的属性放在这里
changelog {
    version = providers.gradleProperty("plugin.version").get()
    repositoryUrl = providers.gradleProperty("plugin.repository.url").get()
}
