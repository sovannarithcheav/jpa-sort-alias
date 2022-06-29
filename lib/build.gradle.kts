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

apply {
	plugin("maven-publish")
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = project.properties["group"].toString()
			artifactId = project.name
			version = project.properties["version"].toString()
			from(components["kotlin"])
		}
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// spring
	implementation("org.springframework.data:spring-data-jpa:${property("spring-data-version")}")
}