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
}

dependencies {
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.mysql:mysql-connector-j:9.0.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    testRuntimeOnly( "org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.mockito:mockito-core:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
}

tasks.jar {
    manifest.attributes["Main-Class"] = application.mainClass.get()
    val dependencies = configurations.runtimeClasspath.get().map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}
