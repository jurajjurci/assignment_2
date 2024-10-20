import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt.plugin)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val propertiesFile = file("local.properties")
if (propertiesFile.exists()) {
    localProperties.load(FileInputStream(propertiesFile))
} else {
    println("local.properties file not found in core-network module!")
}
val movieApiKey = localProperties.getProperty("TMDB_API_KEY") ?: ""
val movieApiReadAccessToken = localProperties.getProperty("TMDB_API_READ_ACCESS_TOKEN") ?: ""

android {
    namespace = "sk.jurci.core_network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "MOVIE_API_KEY", "\"$movieApiKey\"")
        buildConfigField("String", "MOVIE_API_READ_ACCESS_TOKEN", "\"$movieApiReadAccessToken\"")

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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    // Dependency Injection
    implementation(libs.androidx.dagger.hilt)
    ksp(libs.androidx.dagger.hilt.compiler)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.kotlinx.serialization)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}