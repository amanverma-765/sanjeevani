import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ark.sanjeevani"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ark.sanjeevani"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField(
            type = "String",
            name = "supabaseUrl",
            value = properties.getProperty("supabaseUrl")
        )
        buildConfigField(
            type = "String",
            name = "supabaseApiKey",
            value = properties.getProperty("supabaseApiKey")
        )
        buildConfigField(
            type = "String",
            name = "googleClientId",
            value = properties.getProperty("googleClientId")
        )
    }

    buildTypes {
        debug {
            isMinifyEnabled  = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Koin dependencies
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.vm)
    // Ktor dependencies
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization.json)
    // Ktx serialization
    implementation(libs.kotlinx.serialization.json)
    // Coil dependencies
    implementation(libs.coil)
    implementation(libs.coil.core)
    implementation(libs.coil.network.ktor)
    // Vector icons
    implementation(libs.composeIcons.fontAwesome)
    implementation(libs.material.icons.extended)
    // Compose Navigation
    implementation(libs.androidx.compose.navigation)
    // Connectivity
    implementation(libs.connectivity.core)
    implementation(libs.connectivity.android)
    implementation(libs.connectivity.compose.device)
    // Supabase dependencies
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.kt)
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.auth.kt)
    implementation(libs.supabase.realtime.kt)
    implementation(libs.supabase.compose.auth)
    // Others
    implementation(libs.material.kolor)
    implementation(libs.touchlab.kermit)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.compose.shimmer)
    implementation(libs.lottie)
}