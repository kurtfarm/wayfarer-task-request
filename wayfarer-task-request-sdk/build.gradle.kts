plugins {
    id("maven-publish")
}

dependencies {
    api(project(":wayfarer-task-request-app"))
    api("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
