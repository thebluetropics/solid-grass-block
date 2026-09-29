plugins {
	`java-library`
	`maven-publish`
	id("net.neoforged.moddev") version "2.0.147"
	idea
}

version = "0.5.0-alpha"

base {
	archivesName = "solid-grass-block-neoforge-26.1"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
	withSourcesJar()
}

neoForge {
	version = "26.1.0.19-beta"
	runs {
		register("client") {
			client()
		}
		register("server") {
			server()
			programArgument("--nogui")
		}
		register("data") {
			clientData()
			programArguments.addAll("--mod", "solid_grass_block", "--all", "--output", file("src/generated/resources/").absolutePath, "--existing", file("src/main/resources/").absolutePath)
		}
		configureEach {
			systemProperty("forge.logging.markers", "REGISTRIES")
			logLevel = org.slf4j.event.Level.DEBUG
		}
	}
	mods {
		register("solid_grass_block") {
			sourceSet(sourceSets.main.get())
		}
	}
}

sourceSets.main {
	resources {
		srcDir("src/generated/resources")
		exclude("src/generated/**/.cache")
	}
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
		register<MavenPublication>("mavenJava") {
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

tasks.withType(JavaCompile::class.java).configureEach {
	options.encoding = "UTF-8"
	options.release = 25
}

idea {
	module {
		isDownloadSources = true
		isDownloadJavadoc = true
	}
}
