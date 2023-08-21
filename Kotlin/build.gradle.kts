import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.10"
}

group = "com.gildedrose"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("com.zegreatrob.testmints:testmints-bom:10.2.16"))
	implementation(kotlin("stdlib"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

	testImplementation(kotlin("test"))
	testImplementation("com.zegreatrob.testmints:standard")
	testImplementation("com.zegreatrob.testmints:async")
	testImplementation("com.zegreatrob.testmints:minassert")
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions.jvmTarget = "1.8"
}
