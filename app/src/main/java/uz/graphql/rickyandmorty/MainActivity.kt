package uz.graphql.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.ui.Modifier
import uz.graphql.rickyandmorty.ui.theme.RickyAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.rickyandmorty.navigator.StartingNavHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var provider: Navigator.Provider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    StartingNavHost(activity = this, provider = provider)
                }
            }
        }
    }
}