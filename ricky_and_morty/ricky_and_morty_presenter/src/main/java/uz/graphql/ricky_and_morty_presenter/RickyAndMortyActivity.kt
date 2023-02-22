package uz.graphql.ricky_and_morty_presenter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.ricky_and_morty_presenter.screens.RickyAndMortyScreen
import uz.graphql.ricky_and_morty_presenter.ui.theme.RickyAndMortyTheme

class RickyAndMortyActivity : ComponentActivity() {
    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, RickyAndMortyActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    RickyAndMortyScreen(navController = navController)
                }
            }
        }
    }
}

object GoToRickyAndMortyActivity : Navigator {
    override fun navigate(activity: Activity) {
        RickyAndMortyActivity.launchActivity(activity)
    }
}