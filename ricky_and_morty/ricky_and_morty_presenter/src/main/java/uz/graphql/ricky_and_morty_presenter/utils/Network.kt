package uz.graphql.ricky_and_morty_presenter.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick_and_morty.common_utills.other.extention.isNetworkAvailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/24/2023 8:23 PM for Ricky And Morty.
 */
fun ViewModel.loadWithNetworkNetwork(
    context: Context,
    errorBlock: suspend () -> Unit = {},
    block: suspend () -> Unit
) {
    viewModelScope.launch {
        if (context.isNetworkAvailable())
            block()
        else {
            errorBlock.invoke()
            var count = 0
            while (count >= 0) {
                delay(1000)
                count++
                if (context.isNetworkAvailable()) {
                    block.invoke()
                    count = -1
                }
                if (count > 20)
                    count = -1
            }
        }
    }
}