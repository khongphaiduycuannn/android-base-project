plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
    id("com.google.devtools.ksp")

    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.duycuannn.base_project"
    compileSdk = 36

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


    // fragment
    val fragmentVersion = "1.6.1"
    implementation("androidx.fragment:fragment:$fragmentVersion")

    // view model
    val lifecycleVersion = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // navigation component
    val navControllerVersion = "2.8.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navControllerVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navControllerVersion")

    // Hilt
    val hiltVersion = "2.57"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")


    // Epoxy
    val epoxyVersion = "5.1.4"
    implementation("com.airbnb.android:epoxy:$epoxyVersion")
    implementation("com.airbnb.android:epoxy-databinding:$epoxyVersion")
    kapt("com.airbnb.android:epoxy-processor:$epoxyVersion")


    // lottie file
    val lottieVersion = "6.1.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")

    // glide
    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    // sdp
    val sdpVersion = "1.1.1"
    implementation("com.intuit.sdp:sdp-android:$sdpVersion")
}