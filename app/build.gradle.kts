plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.example.recipefinder"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.recipefinder"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx. lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("android.activity:activity-compose:1.7.2")
    implementation(platform("android. compose: compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("android.compose.vi:vi-graphics")
    implementation("android.compose.vi:vi-tooling-preview")
    implementation("android.compose.material3:material3:1.1.1")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha04")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("android.test.ext:junit:1.1.5")
    androidTestImplementation("androidx. test. espresso: espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.vi:vi-test-junit4")
    debugImplementation("androidx.compose.ui:vi-tooling")
    debugImplementation("androidx.compose.ui:vi-test-manifest")

    implementation("io.ktor:ktor-client-cio-jvm:2.3.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("io.coil-kt:coil-compose:2.4.0")
}