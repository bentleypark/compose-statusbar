import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Base64

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.nexus.vanniktech.publish)
    id("signing")
}


kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

android {
    namespace = "io.github.bentleypark.compose_statusbar"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testOptions.targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

//signing {
//    useGpgCmd()
//    sign(publishing.publications)
//    tasks.withType<AbstractPublishToMaven>().configureEach {
//        dependsOn(tasks.withType<Sign>())
//    }
//}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//    signAllPublications()

    val artifactId = "compose-statusbar"

    coordinates("io.github.bentleypark", artifactId, "1.0.1")

    pom {
        name.set(artifactId)
        description.set("A KMP library for Status bar configuration in Compose")
        inceptionYear.set("2024")
        url.set("https://github.com/bentleypark/compose-statusbar")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("bentleypark")
                name.set("BentleyPark")
                url.set("https://github.com/bentleypark/")
            }
        }
        scm {
            url.set("https://github.com/bentleypark/compose-statusbar")
            connection.set("scm:git:git://github.com/bentleypark/compose-statusbar.git")
            developerConnection.set("scm:git:ssh://git@github.com/bentleypark/compose-statusbar.git")
        }
    }
}

signing {
    val signingKey = project.findProperty("signing.key") as String?
    val signingPassword = project.findProperty("signing.password") as String?

    if (!signingKey.isNullOrBlank() && !signingPassword.isNullOrBlank()) {
        useInMemoryPgpKeys(
            String(Base64.getDecoder().decode(signingKey)),
            signingPassword
        )
        sign(publishing.publications)
    }
}


