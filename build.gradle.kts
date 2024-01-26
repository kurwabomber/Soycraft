import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.razorclan"
version = "1.0-INDEV"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.iridium:IridiumColorAPI:1.0.6")
    implementation("xyz.xenondevs.invui:invui:1.24")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    named<ShadowJar>("shadowJar") {
        relocate("com.iridium.iridiumcolorapi", "net.razorclan.Soycraft.shaded.iridiumcolorapi")
    }
}

application {
    mainClass.set("net.razorclan.Soycraft.Main")
}

task<Copy>("moveToOutput") {
    mustRunAfter("shadowJar")
    from("build/libs/Soycraft-1.0-INDEV-all.jar")
    into(File("/home/davidn/Documents/Servers/SoyblockTestServer/plugins").absolutePath)
}