plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.ndmquan.base_project"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /* ----------------------------- Logic ----------------------------- */

    // fragment
    val fragmentVersion = "1.8.2"
    implementation("androidx.fragment:fragment:$fragmentVersion")

    // view model
    val lifecycleVersion = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // navigation component
    val navControllerVersion = "2.8.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navControllerVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navControllerVersion")

    // mmkv
    val mmkvVersion = "2.2.1"
    implementation("com.tencent:mmkv-static:$mmkvVersion")


    /* ----------------------------- UI ----------------------------- */

    // lottie file
    val lottieVersion = "6.1.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")

    // glide
    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    // sdp
    val sdpVersion = "1.1.1"
    implementation("com.intuit.sdp:sdp-android:$sdpVersion")

    // preferences
    val preferencesVersion = "1.2.1"
    implementation("androidx.preference:preference-ktx:$preferencesVersion")
}