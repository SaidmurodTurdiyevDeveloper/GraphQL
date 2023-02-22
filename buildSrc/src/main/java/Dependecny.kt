/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/20/2023 11:25 PM for Ricky And Morty.
 */

object Versions {
    //common android dependency
    const val androidx_core = "1.9.0"
    const val androidx_lifecycle = "2.5.1"
    const val androidx_appcompat = "1.6.1"

    //compose
    const val compose_activity = "1.6.1"
    const val compose_ui = "1.3.3"
    const val compose_material = "1.3.1"
    const val compose_navigation = "2.5.3"

    //test
    const val j_unit = "4.13.2"
    const val androidx_j_unit = "1.1.5"
    const val androidx_espresso = "3.5.1"

    //google
    const val google_material = "1.8.0"

    //dagger
    const val dagger_hilt = "2.44"
    const val dagger_hilt_navigation_compose = "1.0.0"
    //image loader
    const val coil_compose="2.2.2"
    //accompanist
    const val accompanist_pager="0.19.0"
    const val accompanist_systemuicontroller="0.27.0"
    //apollo
    const val apollo_runtime="3.7.3"
}

object CommonAndroidDependencies {
    //implementation
    const val core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
}

object ComposeDependencies {
    //implementation
    const val activity = "androidx.activity:activity-compose:${Versions.compose_activity}"
    const val ui = "androidx.compose.ui:ui:${Versions.compose_ui}"
    const val ui_util = "androidx.compose.ui:ui-util:${Versions.compose_ui}"
    const val ui_tooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_ui}"
    const val material = "androidx.compose.material:material:${Versions.compose_material}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.compose_navigation}"
}

object AndroidTestDependencies {
    //testImplementation
    const val j_unit = "junit:junit:${Versions.j_unit}"

    //androidTestImplementation
    const val androidx_j_unit = "androidx.test.ext:junit:${Versions.androidx_j_unit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
    const val compose_j_unit = "androidx.compose.ui:ui-test-junit4:${Versions.compose_ui}"

    //debugImplementation
    const val compose_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_ui}"
    const val compose_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose_ui}"
}

object GoogleDependencies {
    //implementation
    const val material = "com.google.android.material:material:${Versions.google_material}"
}

object DaggerHilt {
    //implementation
    const val android = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
    const val navigation_compose = "androidx.hilt:hilt-navigation-compose:${Versions.dagger_hilt_navigation_compose}"

    //kapt
    const val compiler = "com.google.dagger:hilt-compiler:${Versions.dagger_hilt}"
}
object ImageLoader{
    //implementation
    const val coil_compose="io.coil-kt:coil-compose:${Versions.coil_compose}"
}
object Accompanist{
    const val pager="com.google.accompanist:accompanist-pager:${Versions.accompanist_pager}"
    const val pager_indicator="com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist_pager}"
    const val system_ui_controller="com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist_systemuicontroller}"
}
object Apollo{
    const val runtime="com.apollographql.apollo3:apollo-runtime:${Versions.apollo_runtime}"
}