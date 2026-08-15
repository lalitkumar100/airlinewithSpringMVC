plugins {
    id("java")
    id("war")
}

group = "org.crimsonlogic"
version = "1.0-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.springframework:spring-webmvc:5.3.39")
    implementation("org.springframework:spring-jdbc:5.3.39")

    implementation("org.mybatis:mybatis:3.5.19")
    implementation("org.mybatis:mybatis-spring:2.1.2")

    implementation("com.mysql:mysql-connector-j:8.4.0")

    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")

    implementation("javax.servlet:jstl:1.2")
   
implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
    testImplementation("org.springframework:spring-test:5.3.39")




        // JJWT API
        implementation("io.jsonwebtoken:jjwt-api:0.12.6")
        // JJWT Runtime Impl
        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
        // JJWT Jackson Extension for JSON mapping
        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")


}

tasks.test {
    useJUnitPlatform()
}

