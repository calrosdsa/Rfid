//import com.android.build.api.dsl.ManagedVirtualDevice

//plugins {
//    id("com.android.test")
//    id("org.jetbrains.kotlin.android")
//}
import com.android.build.gradle.internal.dsl.ManagedVirtualDevice

plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace  = "com.example.benchmark"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        devices {
            add(
                ManagedVirtualDevice("pixel2api31").apply {
                    device = "Pixel 2"
                    apiLevel = 31
                    systemImageSource = "aosp"
//                    abi = "x86"
                }
            )
        }
    }





    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = buildTypes["debug"].signingConfig
            matchingFallbacks += "release"
            isMinifyEnabled = true
            proguardFiles("benchmark-rules.pro")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
//    implementation("androidx.test.ext:junit:1.1.4")
//    implementation("androidx.test.espresso:espresso-core:3.5.0")
//    implementation("androidx.test.uiautomator:uiautomator:2.2.0")
//    implementation("androidx.benchmark:benchmark-macro-junit4:1.1.1")
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.test.junit)
    implementation(libs.kotlin.coroutines.android)
}


androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}
