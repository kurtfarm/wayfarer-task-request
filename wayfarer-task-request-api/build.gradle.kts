dependencies {
    implementation(project(":wayfarer-task-request-app"))
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}
