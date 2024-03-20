plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.itmo.se"
version = "2.2"
description = "Lab5"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.run<JavaExec> {
    standardInput = System.`in`
}

application {
    mainClass = "ru.itmo.se.console.Main"
}
dependencies {
    implementation("com.google.code.gson:gson:2.10.1")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}
tasks.jar {
    manifest.attributes["Main-Class"] = "ru.itmo.se.console.Main"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

