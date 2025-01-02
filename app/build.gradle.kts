import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.hikeit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hikeit"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
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

    defaultConfig {
        testInstrumentationRunner = "com.example.hikeit.CustomTestRunner"

        val mapsKeyFile = project.rootProject.file("secret.properties")
        val properties = Properties()
        properties.load(mapsKeyFile.inputStream())

        val apiKey = properties.getProperty("MAPS_API_KEY") ?: ""

        manifestPlaceholders["GOOGLE_KEY"] = apiKey
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.bundles.koin)

    implementation(libs.bundles.ktor)

    implementation(libs.bundles.maps)

    implementation(libs.bundles.credentials)

    implementation(libs.coil)
    implementation(libs.google.navigation)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.konsume.xml)

    implementation(libs.apollo.runtime)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)

    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.navigation.test)
}

apollo {
    service("service") {
        packageName.set("com.example.hikeit")
        introspection {
            endpointUrl.set("http://localhost:8080/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}