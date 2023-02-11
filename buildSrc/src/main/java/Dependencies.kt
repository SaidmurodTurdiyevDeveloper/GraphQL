/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/11/2023 9:02 PM for Rick And Morty GraphQL.
 */
object Versions {
    //deps
    const val core = "1.9.0"
    const val appCompat = "1.6.0"
    const val material = "1.7.0"
    const val constraint = "2.1.4"
    const val legacy = "1.0.0"
    const val timber = "5.0.1"

    //test
    const val j_unit = "4.13.2"

    //androidTest
    const val android_j_unit = "1.1.5"
    const val espresso = "3.5.1"

    //lifeCycle
    const val lifecycle = "2.5.1"

    //compose
    const val compose_ui = "1.3.3"
    const val compose_boom = "2022.10.00"
    const val compose_activity = "1.6.1"
    const val compose_material_3 = "1.1.0-alpha03"
    const val compose_material= "1.3.1"

    //navigation
    const val navigation = "2.5.3"

    //imageLoader
    const val glide = "4.14.2"
    const val picasso = "2.8"

    //coroutine
    const val coroutine = "1.6.4"

    //viewBinding
    const val view_binding = "1.5.6"

    //Dagger
    const val dagger_hilt = "2.44.2"

    //Permission
    const val dexter = "6.2.3"

    //Room
    const val room = "2.4.3"

    //Paging
    const val paging = "3.1.1"
    const val compose_paging = "1.0.0-alpha17"

    //Splash
    const val circle_indicator = "2.1.6"
    const val dots_indicator = "4.3"

    //Carousel
    const val image_carousel = "2.1.0"
    const val image_slider = "0.1.0"

    //Image
    const val compressor = "3.0.1"
    const val circle_image_hdodenhof = "3.1.0"
    const val circle_image_mikhaellopez = "4.3.0"

    //Expansion panel
    const val expansion_panel = "1.2.4"

    //Coil
    const val coil = "2.2.2"

    //WorkManager
    const val work_manager = "2.7.1"

    // Retrofit
    const val retrofit_2 = "2.9.0"

    //Okkhttp
    const val okhttp_3 = "5.0.0-alpha.2"
    const val okhttp_3_logging_interceptor = "5.0.0-alpha.6"

    //Chucker
    const val chucker = "3.5.2"

    //OverScroll
    const val over_scroll_decor = "1.1.1"

    //Chart
    const val mpa_chart = "v3.1.0"
    const val any_chart = "1.1.2"

    //Flexbox
    const val flex_box = "3.0.0"

    //SwitchButton
    const val zcweng = "0.0.3@aar"

    //Network
    const val job_get_abu = "v2.0.0"

    //Shimmer
    const val shimmer = "0.5.0"

    //TableView
    const val evrencoskun = "v0.8.9.4"

    //LeakCanary
    const val leak_canary = "1.3.1"
    const val debug_leak_canary = "2.9.1"

    //Lottie
    const val lottie = "4.2.2"

    //Apollo
    const val apollo_runtime = "3.7.3"
    const val apollo_sqlite = "3.7.3"
    const val apollo_api = "3.7.3"
    const val apollo_support = "1.0.0"

    //Mask Edittext
    const val pinball_83 = "1.0.4"
    const val otp_mukesh_solanki = "2.1.2"

    //Swipe
    const val swipe_refresh_layout = "1.1.0"
}

//androidx.core:core-ktx:1.9.0
object Deps {
    //implementation
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val app_compat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layoyt = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Tests {
    //testImplementation
    const val j_unit = "junit:junit:${Versions.j_unit}"
}

object AndroidTest {
    const val compose_j_unit = "androidx.compose.ui:ui-test-junit4:${Versions.compose_ui}"
    //androidTestImplementation
    const val j_unit = "androidx.test.ext:junit:${Versions.android_j_unit}"
    const val compose_j_unit_ = "androidx.compose.ui:ui-test-junit4"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object LifeCycle {
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val view_model = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"
    const val view_model_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
}

object ComposeDeps {
    //implementation
    const val activity = "androidx.activity:activity-compose:${Versions.compose_activity}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose_ui}"
    const val ui_tooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_ui}"
    const val compose_material_3 = "androidx.compose.material3:material3:${Versions.compose_material_3}"
    const val ui_ = "androidx.compose.ui:ui"
    const val ui_graphics_ = "androidx.compose.ui:ui-graphics"
    const val ui_tooling_ = "androidx.compose.ui:ui-tooling-preview"
    const val compose_material_3_ = "androidx.compose.material3:material3"
    const val compose_material = "androidx.compose.material:material:${Versions.compose_material}"
    //debugImplementation
    const val compose_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_ui}"
    const val compose_tooling_ = "androidx.compose.ui:ui-tooling"
    const val compose_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose_ui}"
    const val compose_manifest_ = "androidx.compose.ui:ui-test-manifest"
    //implementation platform(), androidTestImplementation platform()
    const val compose_boom = "androidx.compose:compose-bom:${Versions.compose_boom}"
}

object DebugDeps {
    const val compose_ui_tTooling = "androidx.compose.ui:ui-tooling:${Versions.compose_ui}"
    const val compose_ui_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose_ui}"
}

object Navigation {
    //implementation
    const val module_support = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val jetpack = "androidx.navigation:navigation-compose:${Versions.navigation}"

    //androidTestImplementation
    const val testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
}

object ImageLoader {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_annotation = "com.github.bumptech.glide:annotations:${Versions.glide}"
    const val glide_integration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Coroutine {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
}

object ViewBinding {
    //implementation
    const val delegate = "com.github.kirich1409:viewbindingpropertydelegate:${Versions.view_binding}"
    const val noreflection = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.view_binding}"
}

object Dagger {
    //implementation
    const val hilt = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"

    //kapt
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}"
}

object Permission {
    const val dexter = "com.karumi:dexter:${Versions.dexter}"
}

object Room {
    //implementation
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
    const val guavu = "androidx.room:room-guava:${Versions.room}"
    //kapt
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    //annotationProcessor
//    const val compiler = "androidx.room:room-compiler:${Versions.room}"
}

object Paging {
    const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
    const val common = "androidx.paging:paging-common:${Versions.paging}"
    const val rxJava2 = "androidx.paging:paging-rxjava2:${Versions.paging}"
    const val rxJava3 = "androidx.paging:paging-rxjava3:${Versions.paging}"
    const val guavu = "androidx.paging:paging-guava:${Versions.paging}"
    const val compose = "androidx.paging:paging-compose:${Versions.compose_paging}"
}

object SplashDeps {
    const val circle_indicator = "me.relex:circleindicator:${Versions.circle_indicator}"
    const val dots_indicator = "com.tbuonomo:dotsindicator:${Versions.dots_indicator}"
}

object Carousel {
    const val image_carousel = "org.imaginativeworld.whynotimagecarousel:whynotimagecarousel:${Versions.image_carousel}"
    const val image_slider = "com.github.denzcoskun:ImageSlideshow:${Versions.image_slider}"
}

object Image {
    const val compressor = "id.zelory:compressor:${Versions.compressor}"
    const val circle_image_hdodenhof = "de.hdodenhof:circleimageview:${Versions.circle_image_hdodenhof}"
    const val circle_image_mikhaellopez = "com.mikhaellopez:circularimageview:${Versions.circle_image_mikhaellopez}"
}

object Expansion {
    //implementation
    const val expansion_panel = "com.github.florent37:expansionpanel:${Versions.expansion_panel}"
}

object Coil {
    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
}

object WorkManager {
    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work_manager}"
}

object Retrofit {
    const val retrofit_2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit_2}"
    const val converter_gson_retrofit_2 = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_2}"
    const val gson_retrofit_2 = "com.google.code.gson:gson:${Versions.retrofit_2}"
}

object Okhttp {
    //implementation
    const val okhttp_3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp_3}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_3_logging_interceptor}"
}

object Chucker {
    const val library = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val library_no_op = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
}

object OverScroll {
    const val over_scroll_decor = "io.github.everythingme:overscroll-decor-android:${Versions.over_scroll_decor}"
}

object Chart {
    const val mpa_chart = "com.github.PhilJay:MPAndroidChart:${Versions.mpa_chart}"
    const val any_chart = "com.github.AnyChart:AnyChart-Android:${Versions.any_chart}"
}

object FlexBox {
    const val flex_box = "com.google.android.flexbox:flexbox:${Versions.flex_box}"
}

object SwitchButton {
    const val zcweng = "com.github.zcweng:switch-button:${Versions.zcweng}"
}

object Network {
    const val job_get_abu = "com.github.JobGetabu:DroidNet:${Versions.job_get_abu}"
}

object Shimmer {
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
}

object TableView {
    const val evrencoskun = "com.github.evrencoskun:TableView:${Versions.evrencoskun}"
}

object LeakCanary {
    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"
    const val leak_canary_no_op = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leak_canary}"
    const val debug_leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.debug_leak_canary}"
}

object Lottie {
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
}

object Apollo {
    const val apollo_runtime = "com.apollographql.apollo3:apollo-runtime:${Versions.apollo_runtime}"
    const val apollo_support = "com.apollographql.apollo:apollo-android-support:${Versions.apollo_support}"
    const val apollo_sqlite = "com.apollographql.apollo3:apollo-normalized-cache-sqlite:${Versions.apollo_sqlite}"
    const val apollo_api = "com.apollographql.apollo3:apollo-api:${Versions.apollo_api}"
}

object MaskEdittext {
    const val pinball_83 = "com.github.pinball83:masked-edittext:${Versions.pinball_83}"
    const val otp_mukesh_solanki = "com.github.pinball83:masked-edittext:${Versions.otp_mukesh_solanki}"
}

object Swipe {
    const val swipe_refresh_layout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"
}
