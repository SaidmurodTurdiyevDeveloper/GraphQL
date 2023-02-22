package uz.graphql.rickyandmorty.preseenter.viewModel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick_and_morty.common_utills.other.extention.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.graphql.common_utills.dataStore.SharedDatabase
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 11:59 AM for Rick And Morty GraphQL.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext
    private var context: Context,
    private var store: SharedDatabase
) : ViewModel() {
    private var _connectedInternet: MutableState<NetworkData> = mutableStateOf(NetworkData())
    val connectedInternet: State<NetworkData> = _connectedInternet


    private var _uiEvent = Channel<SplashUiEvent>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    init {
        start()
    }

    fun start() {
        try {
            viewModelScope.launch {
                _connectedInternet.value = connectedInternet.value.copy(
                    hasNetwork = context.isNetworkAvailable()
                )
                var cond = true
                while (cond) {
                    if (context.isNetworkAvailable()) {
                        _connectedInternet.value = connectedInternet.value.copy(
                            hasNetwork = true
                        )
                        _uiEvent.send(SplashUiEvent.Loading)
                        delay(1500)
                        open()
                        cond = false
                    } else {
                        delay(1000)
                    }
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _connectedInternet.value = connectedInternet.value.copy(
                    error = e.message ?: "Unknown error"
                )
                _uiEvent.send(SplashUiEvent.ShowError)
            }
        }
    }

    private fun open() {
        viewModelScope.launch {
            if (store.firstEnter)
                _uiEvent.send(SplashUiEvent.OpenHome)
            else
                _uiEvent.send(SplashUiEvent.OpenStarting)
        }
    }

    data class NetworkData(
        val hasNetwork: Boolean = true,
        val error: String? = null
    )

    sealed class SplashUiEvent {
        object ShowError : SplashUiEvent()
        object Loading : SplashUiEvent()
        object OpenHome : SplashUiEvent()
        object OpenStarting : SplashUiEvent()
    }
}