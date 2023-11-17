plugins {
    id("java")
}


group = "net.razorclan"
version = "1.0-INDEV"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
task<Copy>("moveToOutput") {
    mustRunAfter("jar")
    from("build/libs/Soycraft-1.0-INDEV.jar")
    into(File("/home/davidn/Documents/Servers/SoyblockTestServer/plugins").absolutePath)
}