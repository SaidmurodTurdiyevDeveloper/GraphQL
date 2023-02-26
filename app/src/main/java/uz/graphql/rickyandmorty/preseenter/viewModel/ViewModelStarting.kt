package uz.graphql.rickyandmorty.preseenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.graphql.common_utills.dataStore.SharedDatabase
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 11:59 AM for Rick And Morty GraphQL.
 */
@HiltViewModel
class ViewModelStarting @Inject constructor(
    private var store: SharedDatabase
) : ViewModel() {

    private var _uiEvent = Channel<EventViewModelStarting>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    fun open(event: EventUiStarting) {
        viewModelScope.launch {
            when (event) {
                EventUiStarting.OpenHome -> {
                    store.firstEnter = true
                    _uiEvent.send(EventViewModelStarting.OpenHome)
                }
            }
        }
    }


    sealed class EventUiStarting {

        object OpenHome : EventUiStarting()
    }

    sealed class EventViewModelStarting {

        object OpenHome : EventViewModelStarting()
    }
}