val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kmongo: String by project

plugins {
    application
    kotlin("jvm") version "1.5.10"
}



group = "com.mynote"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

//val fatJar = task("fatJar", type = Jar::class) {
//    manifest {
//        attributes["Main-Class"] = "com.mynote"
//    }
//    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
//    with(tasks.jar.get() as CopySpec)
//}
//
//tasks {
//    "build" {
//        dependsOn(fatJar)
//    }
//}
repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("org.litote.kmongo:kmongo:$kmongo")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")