plugins {
    application
    kotlin("jvm") version "2.0.0"
}

val sdkVersion = "0.1.1" // x-release-please-version

group = "net.saturnbot.example"
version = "plugin"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("net.saturn-bot:saturn-bot-plugin-kotlin:$sdkVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
application {
    mainClass.set("net.saturnbot.example.MainKt")
}
tasks {
    val fatJar =
        register<Jar>("fatJar") {
            dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
            archiveClassifier.set("standalone")
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
            val sourcesMain = sourceSets.main.get()
            val contents =
                configurations.runtimeClasspath
                    .get()
                    .map { if (it.isDirectory) it else zipTree(it) } +
                    sourcesMain.output
            from(contents)
        }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}
