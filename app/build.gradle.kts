plugins {
<<<<<<< HEAD
    id("com.android.application") version "8.6.0"
    id("org.jetbrains.kotlin.android") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.whitesymphonymobil"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.whitesymphonymobil"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
=======
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.proyecto_musica"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.proyecto_musica"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

>>>>>>> 90aff6478ca9c687abf947b7ce272dd117ec4b1b
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
<<<<<<< HEAD

    buildFeatures { compose = true }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    kotlinOptions { jvmTarget = "17" }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
=======
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
>>>>>>> 90aff6478ca9c687abf947b7ce272dd117ec4b1b
    }
}

dependencies {
<<<<<<< HEAD
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")


    implementation("androidx.compose.ui:ui:1.7.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.3")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.3")
    implementation("androidx.appcompat:appcompat:1.7.0")
}
=======

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("androidx.camera:camera-core:1.2.3")
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("androidx.activity:activity-ktx:1.9.0")


}
>>>>>>> 90aff6478ca9c687abf947b7ce272dd117ec4b1b
