import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt.plugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val properties = Properties()
properties.load(FileInputStream(project.rootProject.file("gradle.properties")))
val versionCodeValue = properties.getProperty("versionCode")
val versionNumberValue = properties.getProperty("versionName")

android {
    namespace = "sk.jurci.assignment_2"
    compileSdk = 34

    defaultConfig {
        applicationId = "sk.jurci.assignment_2"
        minSdk = 24
        targetSdk = 34
        versionCode = versionCodeValue.toInt()
        versionName = versionNumberValue

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(project(":core-repository"))
    implementation(project(":core-datastore"))
    implementation(project(":feature-movie"))
    implementation(project(":feature-settings"))

    // Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.compose)

    // Dependency Injection
    implementation(libs.androidx.dagger.hilt)
    ksp(libs.androidx.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation)

    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}