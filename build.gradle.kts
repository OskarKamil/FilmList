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
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    // https://mvnrepository.com/artifact/org.ini4j/ini4j
    implementation("org.ini4j:ini4j:0.5.4")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("javagui.HelloFX")
}