// Este é o arquivo /app/build.gradle.kts

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)


}

android {
    // !! MUDE "com.example.myapplication" para o seu namespace !!
    namespace = "com.example.ddm_nasa_explorer"
    compileSdk = 34 // Recomendo usar 34, que é a API estável atual

    defaultConfig {
        // !! MUDE "com.example.myapplication" para o seu ID !!
        applicationId = "com.example.ddm_nasa_explorer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // --- HABILITA O COMPOSE ---
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Usa a versão do compilador do seu libs
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose) // Essencial para setContent

    // --- COMPOSE BOM (Bill of Materials) ---
    implementation(platform(libs.androidx.compose.bom))

    // --- Compose UI ---
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.compose.runtime:runtime-livedata:1.9.4")
    debugImplementation(libs.androidx.compose.ui.tooling)


    // --- ViewModel & Navegação ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // --- API (Retrofit) ---
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // --- Carregamento de Imagem (Coil) ---
    implementation(libs.coil.compose)
    implementation("io.coil-kt:coil-svg:2.7.0")


    // --- Testes  ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Room ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // --- Hilt ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

}

configurations.all {
    resolutionStrategy {
        force(
            "androidx.compose.ui:ui:1.6.8",
            "androidx.compose.ui:ui-graphics:1.6.8",
            "androidx.compose.ui:ui-text:1.6.8",
            "androidx.compose.foundation:foundation:1.6.8",
            "androidx.compose.foundation:foundation-layout:1.6.8",
            "androidx.compose.animation:animation:1.6.8",
            "androidx.compose.animation:animation-core:1.6.8",
            "androidx.compose.runtime:runtime:1.6.8",
            "androidx.compose.runtime:runtime-livedata:1.6.8",
            "androidx.compose.runtime:runtime-saveable:1.6.8",
            "androidx.compose.material:material:1.6.8",
            "androidx.compose.material:material-icons-extended:1.6.8",
            "androidx.compose.ui:ui-tooling:1.6.8",
            "androidx.compose.ui:ui-tooling-preview:1.6.8"
        )
    }
}
