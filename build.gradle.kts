plugins {
	id("net.fabricmc.fabric-loom-remap") version "1.18-SNAPSHOT"
	id("maven-publish")
}

version = "0.5.0-beta"

base {
	archivesName = "solid-grass-block-fabric-1.21.11"
}

repositories {
	maven {
		name = "Terraformers"
		url = uri("https://maven.terraformersmc.com/")
	}
}

loom {
	splitEnvironmentSourceSets()
	runs {
		named("client") {
			runDir = "run/client"
		}
		named("server") {
			runDir = "run/server"
		}
	}
	mods {
		create("example_mod") {
			sourceSet("main")
			sourceSet("client")
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:1.21.11")
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc:fabric-loader:0.19.5")
	modImplementation("net.fabricmc.fabric-api:fabric-api:0.141.6+1.21.11")
	modImplementation("com.terraformersmc:modmenu:17.0.1")
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21

	withSourcesJar()
}

tasks.withType(JavaCompile::class.java).configureEach {
	options.encoding = "UTF-8"
	options.release = 21
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
			version = project.version.toString()
			from(components["java"])
		}
	}
	repositories {
		maven {
			name = "GithubPackages"
			url = uri("https://maven.pkg.github.com/thebluetropics/solid-grass-block")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
		mavenLocal()
	}
}
