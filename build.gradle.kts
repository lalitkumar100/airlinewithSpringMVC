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

    // =========================================================
    // TESTING
    // =========================================================

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework:spring-test:5.3.39")


    // =========================================================
    // SPRING MVC
    // =========================================================

    implementation("org.springframework:spring-webmvc:5.3.39")
    implementation("org.springframework:spring-jdbc:5.3.39")
    implementation("org.springframework:spring-orm:5.3.39")
    implementation("org.springframework.data:spring-data-jpa:2.7.18")

    // =========================================================
    // HIBERNATE
    // =========================================================

    implementation("org.hibernate:hibernate-core:5.6.15.Final")



    // =========================================================
    // MYBATIS (Leaving unused as requested)
    // =========================================================

    implementation("org.mybatis:mybatis:3.5.19")
    implementation("org.mybatis:mybatis-spring:2.1.2")

    // =========================================================
    // SPRING DATA JPA & HIBERNATE
    // =========================================================
    implementation("org.springframework.data:spring-data-jpa:2.7.18")
    implementation("org.hibernate:hibernate-core:5.6.15.Final")



    // =========================================================
    // MYSQL
    // =========================================================

    implementation("com.mysql:mysql-connector-j:8.4.0")


    // =========================================================
    // SERVLET / JSP
    // =========================================================

    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")

    implementation("javax.servlet:jstl:1.2")


    // =========================================================
    // JACKSON
    // =========================================================

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")


    // =========================================================
    // BEAN VALIDATION
    // Spring MVC 5 uses javax.validation
    // =========================================================

    implementation("javax.validation:validation-api:2.0.1.Final")

    implementation("org.hibernate.validator:hibernate-validator:6.2.5.Final")

    implementation("org.glassfish:javax.el:3.0.1-b12")


    // =========================================================
    // JWT
    // =========================================================

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")

    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
}


tasks.test {
    useJUnitPlatform()
}