dependencies {
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("software.amazon.awssdk:s3")
    api("org.springframework.boot:spring-boot-starter-data-redis")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
