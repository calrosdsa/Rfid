plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.teclu.mobility2"
    compileSdk = 33

    signingConfigs {
       create("release") {
//            storeFile file('C:\\Users\\59175\\AndroidStudioProjects\\TabletMobility\\upload-keystore.jks')
            storeFile = rootProject.file("upload-keystore.jks")
            storePassword = "12ab34cd56ef"
            keyAlias ="upload"
            keyPassword = "12ab34cd56ef"
        }
    }

    defaultConfig {
        applicationId = "com.teclu.mobility2"
//        applicationId = "com.coppernic.mobility"
        minSdk = 26
        versionCode = 10
        versionName = "1.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"



        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["release"]
            isShrinkResources = true
            isMinifyEnabled = true
//            proguardFiles  getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            proguardFiles("proguard-rules.pro")
        }
        debug {
            proguardFiles("proguard-rules.pro")
        }
        create("benchmark") {
            initWith(buildTypes["release"])
            signingConfig = signingConfigs["debug"]
            matchingFallbacks += "release"
            isDebuggable = false
        }
//        create("benchmark") {
//            signingConfig = signingConfigs.getByName("debug")
//            matchingFallbacks += listOf("release")
//            isDebuggable = false
//        }
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        baseline = file("lint-baseline.xml")
        // Disable lintVital. Not needed since lint is run on CI
        checkReleaseBuilds = false
        // Ignore any tests
        ignoreTestSources = true
        // Make the build fail on any lint errors
        abortOnError = true
        // Allow lint to check dependencies
        checkDependencies = true
    }

    //
    //    applicationVariants.all {
    //        kotlin.sourceSets {
    //            getByName(name) {
    //                kotlin.srcDir("build/generated/ksp/$name/kotlin")
    //            }
    //        }
    //    }
}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg( "room.incremental", "true")
}

dependencies {
//    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.emoji)

    implementation(libs.compose.foundation.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material.material)
//    implementation(libs.compose.material.iconsext)
    implementation(libs.compose.animation.animation)
    implementation(libs.compose.ui.tooling)

    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.navigation.material)

    implementation(libs.threeTenAbp)

    implementation(libs.timber)

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.androidx.profileinstaller)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.hilt.library)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit.retrofit)
    implementation(libs.store)

//    implementation(libs.androidx.work.runtime)
//    implementation(libs.hilt.work)
//    kapt(libs.hilt.compiler)
    implementation("androidx.work:work-runtime-ktx:2.7.1")
//    implementation("androidx.work:work-runtime-ktx:2.7.1")
    implementation("androidx.hilt:hilt-work:1.0.0")
    // When using Kotlin.
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    api(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.coil.coil)
    implementation(libs.coil.compose)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.accompanist.swiperefresh)
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(libs.retrofit.gsonConverter)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation("io.github.hakky54:sslcontext-kickstart:7.2.1")

    implementation("com.airbnb.android:lottie-compose:5.2.0")


    val camerax_version = "1.0.2"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:1.0.0-alpha29")

    //Barcode
    implementation("com.google.mlkit:barcode-scanning:17.0.0")

    //Camera Permission
    implementation("com.google.accompanist:accompanist-permissions:0.19.0")
    implementation("com.google.guava:guava:31.0.1-android")
}
