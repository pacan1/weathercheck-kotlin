buildscript {
    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.0.RELEASE")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8")
    }
}

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("plugin.jpa") version "1.3.50"
    idea
    id("se.thinkcode.cucumber-runner") version "0.0.8"
    id("com.github.spacialcircumstances.gradle-cucumber-reporting") version "0.1.21"
    id("io.qameta.allure") version "2.8.1"
}

repositories {
    maven("https://repo1.maven.org/maven2/") {}
    mavenLocal()
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }
}

val cucumberVersion = "5.6.0"
val springVersion = "5.2.1.RELEASE"
val restAssuredVersion = "4.2.0"
val kotlinVersion = "1.3.61"
val junitVersion = "5.6.1"
val jetBrainsKotlinVersion = "1.3.50"
val allureVersion = "2.13.2"
//val aspectjweaverVersion = "1.9.5"
//val aspectjWeaverAgent: Configuration by configurations.creating


dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$jetBrainsKotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$jetBrainsKotlinVersion")

    implementation("io.cucumber:cucumber-java:$cucumberVersion")
    implementation("io.cucumber:cucumber-java8:$cucumberVersion")
    implementation("io.cucumber:cucumber-spring:$cucumberVersion")

    implementation("io.github.microutils:kotlin-logging:1.7.6")
    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("io.qameta.allure:allure-cucumber5-jvm:$allureVersion")
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    implementation("io.qameta.allure:allure-java-commons:$allureVersion")
    implementation("io.qameta.allure:allure-attachments:$allureVersion")

    implementation("io.rest-assured:rest-assured:${restAssuredVersion}")
    implementation("io.rest-assured:json-path:${restAssuredVersion}")
    implementation("io.rest-assured:json-schema-validator:${restAssuredVersion}")
    implementation("io.rest-assured:kotlin-extensions:${restAssuredVersion}")

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")

    implementation("org.threeten:threetenbp:1.2")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("io.gsonfire:gson-fire:1.8.4")

    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("org.springframework:spring-context:${springVersion}")
    implementation("org.springframework:spring-test:${springVersion}")

    implementation("org.hamcrest:hamcrest-junit:2.0.0.0")

    implementation("org.awaitility:awaitility-kotlin:4.0.2")

    implementation("com.github.noconnor:junitperf:1.16.0")
//    aspectjWeaverAgent("org.aspectj:aspectjweaver:$aspectjweaverVersion")

}

//val javaagent = "-javaagent:${aspectjWeaverAgent.singleFile}"


cucumber {
    threads = "1"
    strict = "true"
    glue = "com.weather"
    featurePath = "${projectDir}/src/test/resources/"
    main = "io.cucumber.core.cli.Main"
    tags = "(@regression) and (not @skip) and (not @issue)"
    plugin = arrayOf(
        "json:build/cucumber-report/data/json/report.json",
        "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
        "com.weather.infrastructure.restassured.TestListener"
        )
}

allure {
    version = allureVersion
//    aspectjweaver = false
    resultsDir = file(System.getProperty("allure.results.directory", "$projectDir/allure-results"))
    reportDir = file("$projectDir/allure-report")
    useJUnit5 {
        version = allureVersion
    }
}

cucumberReports {
    outputDir = file("build/test-report/html")
    classifications = mapOf("Weather Check" to "API")
    buildId = "1.0"
    projectNameOverride = "Weather Check API Test Report"
    reports = files("build/cucumber-report/data/json/report.json")
    testTasksFinalizedByReport = false
}

tasks.withType<Test> {
    isEnabled = true
    outputs.upToDateWhen { false }
}
