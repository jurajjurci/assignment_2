import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt.plugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

val properties = Properties()
properties.load(FileInputStream(project.rootProject.file("gradle.properties")))
val versionCodeValue = properties.getProperty("versionCode")
val versionNumberValue = properties.getProperty("versionName")
val createdBy = properties.getProperty("createdBy")

android {
    namespace = "sk.jurci.feature_settings"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "VERSION_NUMBER", "\"$versionNumberValue\"")
        buildConfigField("String", "VERSION_CODE", "\"$versionCodeValue\"")
        buildConfigField("String", "CREATED_BY", "\"$createdBy\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(project(":core-datastore"))

    // Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.accompanist.systemuicontroller)

    // Dependency Injection
    implementation(libs.androidx.dagger.hilt)
    ksp(libs.androidx.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}