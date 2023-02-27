package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.common_utills.other.Constants
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.character.CharacterDetailsData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelCharacterDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private var useCase: UseCaseCharacter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<StateScreenOfViewModelCharacterDetails> = mutableStateOf(StateScreenOfViewModelCharacterDetails())
    val state: State<StateScreenOfViewModelCharacterDetails> get() = _state

    private var id = ""

    private val _event = MutableSharedFlow<EventViewModelCharacterDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveCharacterId).let { newId ->
            if (newId != null) {
                id = newId
                loadWithNetworkNetwork(
                    context,
                    errorBlock = {
                        _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
                    }
                ) {
                    load()
                }
            } else {
                _state.value = state.value.copy(error = "Id could not find")
            }
        }
    }

    private suspend fun load() {
        useCase.getCharacter(id).collectLatest { response ->
            when (response) {
                is ResponseData.Error -> {
                    _state.value = state.value.copy(error = response.message)
                }
                is ResponseData.Loading -> {
                    _state.value = state.value.copy(isLoading = response.isLoading)
                }
                is ResponseData.Success -> {
                    _state.value = state.value.copy(character = response.data, error = null)
                }
            }
        }
    }

    fun onEvent(eventUi: EventUICharacterDetails) {
        when (eventUi) {
            is EventUICharacterDetails.OpenEpisodeDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharacterDetails.OpenEpisodeDetailsScreen(eventUi.id))
                }
            }
            is EventUICharacterDetails.OpenLocationDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharacterDetails.OpenLocationDetailsScreen(eventUi.id))
                }
            }
            EventUICharacterDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
        }

    }

    data class StateScreenOfViewModelCharacterDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val character: CharacterDetailsData? = null
    )

    sealed class EventUICharacterDetails {
        data class OpenLocationDetailsScreen(val id: String) : EventUICharacterDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventUICharacterDetails()
        object Refresh : EventUICharacterDetails()
    }

    sealed class EventViewModelCharacterDetails {
        data class OpenLocationDetailsScreen(val id: String) : EventViewModelCharacterDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventViewModelCharacterDetails()
    }
}