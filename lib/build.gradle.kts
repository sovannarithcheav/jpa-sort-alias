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

subprojects {
	apply {
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
				artifactId = project.name
				version = project.properties["version"].toString()
				from(components["java"])
			}
		}
		repositories {
			maven {
				name = "OSSRH"
				url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
				credentials {
					username = System.getenv("MAVEN_USERNAME")
					password = System.getenv("MAVEN_PASSWORD")
				}
			}
			maven {
				name = "GitHubPackages"
				url = uri("https://maven.pkg.github.com/sovannarithcheav/jpa-sort-alias")
				credentials {
					username = System.getenv("GITHUB_ACTOR")
					password = System.getenv("GITHUB_TOKEN")
				}
			}
		}
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// spring
	implementation("org.springframework.data:spring-data-jpa:${property("spring-data-version")}")
}