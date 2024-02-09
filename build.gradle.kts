plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("org.beryx.jlink") version "2.25.0"
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    implementation("org.ini4j:ini4j:0.5.4")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("javagui.Launcher")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Main-Class"] = "javagui.Launcher"
    }

    from(sourceSets.main.get().output)

    // Include dependencies from the runtime configuration
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) })
}

tasks.withType<JavaCompile> {
    options.isDebug = true
    options.debugOptions.debugLevel = "source,lines"
}
