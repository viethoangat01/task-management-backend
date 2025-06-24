plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.personal"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// --- SPRING CORE ---
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// --- DATABASE ---
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	// --- SPRING SECURITY ---
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")

	// --- SWAGGER / OPENAPI ---
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

	// --- MAPSTRUCT
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // --- LOMBOK
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")

	// --- TESTING ---
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
