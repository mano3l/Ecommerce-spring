import java.io.FileInputStream
import java.util.Properties

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.4")
        classpath("org.flywaydb:flyway-database-postgresql:10.17.3")
    }
}

plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.flywaydb.flyway") version "10.17.3"
}

flyway {
    val dbUrl: String by project
    val dbUser: String by project
    val dbPass: String by project

    url = dbUrl
    user = dbUser
    password = dbPass
    locations = arrayOf("classpath:db/migration")
}

group = "com.personal"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql:10.17.3")
    implementation("org.mapstruct:mapstruct:1.6.2")
    implementation("org.mockito:mockito-core:5.13.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2:2.2.220")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:5.5.0")
    testImplementation("io.rest-assured:spring-mock-mvc:5.5.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun loadEnvProperties(fileName: String): Properties {
    val properties = Properties()
    FileInputStream(fileName).use { properties.load(it) }
    return properties
}

tasks.withType<Test> {
    useJUnitPlatform()
    val envProperties = loadEnvProperties("test.env")
    envProperties["KC_CLIENTID"]?.let { environment("KC_CLIENTID", it) }
    envProperties["KC_SECRET"]?.let { environment("KC_SECRET", it) }
    envProperties["KC_ISSUERURI"]?.let { environment("KC_ISSUERURI", it) }
}

tasks.register<Test>("integrationTests") {
    useJUnitPlatform {
        includeTags("IntegrationTest")
    }
    val envProperties = loadEnvProperties("test.env")
    envProperties["KC_CLIENTID"]?.let { environment("KC_CLIENTID", it) }
    envProperties["KC_SECRET"]?.let { environment("KC_SECRET", it) }
    envProperties["KC_ISSUERURI"]?.let { environment("KC_ISSUERURI", it) }
}

tasks.register<Test>("unitTests") {
    useJUnitPlatform {
        includeTags("UnitTest")
    }
    val envProperties = loadEnvProperties("test.env")
    envProperties["KC_CLIENTID"]?.let { environment("KC_CLIENTID", it) }
    envProperties["KC_SECRET"]?.let { environment("KC_SECRET", it) }
    envProperties["KC_ISSUERURI"]?.let { environment("KC_ISSUERURI", it) }
}