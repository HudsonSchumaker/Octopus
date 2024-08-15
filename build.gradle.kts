plugins {
    id("java")
    id("application")
}

group = "br.com.schumaker"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("br.com.schumaker.octopus.Main")

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

tasks.test {
    useJUnitPlatform()
}