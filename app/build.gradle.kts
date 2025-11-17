plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Es probable que necesites KSP para Room, si no lo tienes, agrégalo:
    // id("com.google.devtools.ksp") version "2.0.21-1.0.21" // Asegúrate que la versión sea compatible
}

android {
    namespace = "com.example.vidasalud2"
    compileSdk = 34 // CORREGIDO: 36 no es una versión estable, 34 sí.

    defaultConfig {
        applicationId = "com.example.vidasalud2"
        minSdk = 24
        targetSdk = 34 // CORREGIDO: 36 no es una versión estable, 34 sí.
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Esta versión es antigua, pero la mantengo.
        // Si actualizas Kotlin, deberás actualizar esto.
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    // --- DEPENDENCIAS PRINCIPALES (Usando tu catálogo 'libs') ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- DEPENDENCIAS MANUALES (Que no estaban en 'libs') ---
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // --- ROOM (Como lo tenías) ---
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    // OJO: Si usas KSP, esto debería ser 'ksp' en lugar de 'annotationProcessor'
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // --- DEPENDENCIAS DE TEST (Usando tu catálogo 'libs') ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- [INICIO] DEPENDENCIAS AGREGADAS (GUÍA 15) ---

    // Kotest (Para pruebas unitarias más legibles)
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")

    // JUnit 5 (El motor de pruebas)
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // MockK (Para simular respuestas de la API)
    testImplementation("io.mockk:mockk:1.13.10")

    // Corrutinas Test (Para probar funciones suspendidas)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // --- [FIN] DEPENDENCIAS AGREGADAS (GUÍA 15) ---
}


tasks.withType<Test> {
    useJUnitPlatform()
}