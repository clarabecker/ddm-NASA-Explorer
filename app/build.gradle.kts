// Este é o arquivo /app/build.gradle.kts

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // <-- Usa o plugin KSP do seu libs
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // --- HABILITA O COMPOSE ---
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Usa a versão do compilador do seu libs
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    // -------------------------

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
    debugImplementation(libs.androidx.compose.ui.tooling)


    // --- ViewModel & Navegação ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // --- API (Retrofit) ---
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // --- Carregamento de Imagem (Coil) ---
    implementation(libs.coil.compose)

    // --- Banco de Dados (Room) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler) // Note o 'ksp' aqui

    // --- Testes (Já estavam) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}