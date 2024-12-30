plugins {
    java
    application

    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    kotlin(
        "jvm"
    )
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

group = "dev.enderman"
version = "1.0-SNAPSHOT"
description = "A plugin to improve Minecraft's combat system."

repositories {
    mavenCentral()

    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://oss.sonatype.org/content/groups/public/")

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation(
        kotlin(
            "stdlib-jdk8"
        )
    )
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()

        val props = mapOf(
                "name" to project.name,
                "version" to project.version,
                "description" to (project.description ?: "No description provided"),
                "apiVersion" to "1.20"
        )

        inputs.properties(props)

        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    build {
        dependsOn(reobfJar)
    }

    assemble {
        dependsOn(reobfJar)
    }
}

application {
    mainClass.set("dev.enderman.minecraft.plugins.combat.better.BetterCombatPlugin")
}
