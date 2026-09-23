plugins {
	id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT"
	id("maven-publish")
}

val modVersion = "0.4.0-alpha"
val artifactName = "solid-grass-block"

base {
  archivesName = "${artifactName}-fabric-26.1"
}

repositories {
  maven {
    name = "Terraformers"
    url = uri("https://maven.terraformersmc.com/")
  }
}

dependencies {
	minecraft("com.mojang:minecraft:26.1")
	implementation("net.fabricmc:fabric-loader:0.18.4")
	implementation("net.fabricmc.fabric-api:fabric-api:0.144.0+26.1")
  implementation("com.terraformersmc:modmenu:18.0.1")
}

loom {
  splitEnvironmentSourceSets()
  runs {
    named("client") {
      runDir = "run/client"
      client()
    }
    named("server") {
      runDir = "run/server"
      server()
    }
  }
  mods {
    create("solid_grass_block") {
      sourceSet("main")
      sourceSet("client")
    }
  }
}

tasks.withType(JavaCompile::class.java).configureEach {
	options.encoding = "UTF-8"
	options.release = 25
}

java {
	toolchain.languageVersion = JavaLanguageVersion.of(25)
	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
	withSourcesJar()
}

tasks.named<Jar>("jar") {
	from("LICENSE") {
		rename {
			"LICENSE-solid_grass_block"
		}
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = "io.github.thebluetropics"
			artifactId = project.base.archivesName.get()
			version = modVersion
			from(components["java"])
		}
	}
	repositories {
		mavenLocal()
	}
}
