import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("maven-publish")
    kotlin("jvm") version "1.5.0"

}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("maven-publish")
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            pom {
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("sovannarithcheav")
                        name.set("Sovannarith Cheav")
                        email.set("cheavsovannarith@gmail.com")
                    }
                }
            }
            groupId = project.properties["group"].toString()
            artifactId = "spring-data-jpa-sort-alias"
            version = project.properties["version"].toString()
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sovannarithcheav/jpa-sort-alias")
            credentials {
                username = System.getenv("GIT_PUBLISH_USER")
                password = System.getenv("GIT_PUBLISH_PASSWORD")
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.data:spring-data-jpa:${property("spring-data-version")}")
}