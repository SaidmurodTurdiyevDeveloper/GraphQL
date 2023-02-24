package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.common_utills.other.Constants
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.character.CharacterListWithDetailsData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelCharactersListWithDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseCharacter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelCharactersListDetails> = mutableStateOf(StateScreenViewModelCharactersListDetails())
    val state: State<StateScreenViewModelCharactersListDetails> get() = _state

    private var idList = emptyList<String>()

    private val _event = MutableSharedFlow<EventViewModelCharactersListWithDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveCharactersIdsList).let { json ->
            idList = try {
                Gson().fromJson<List<String>>(json, String::class.java)
            } catch (e: Exception) {
                emptyList()
            }
            loadWithNetworkNetwork(context,
                errorBlock = {
                    _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
                }) {
                load()
            }
        }
    }

    private suspend fun load() {
        if (idList.isEmpty())
            _state.value = state.value.copy(error = "We cannot find any id")
        else
            useCase.getCharactersListWithIds(idList).collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(characters = response.data, error = null)
                    }
                }
            }
    }

    fun onEvent(eventUi: EventUICharactersListWithDetails) {
        when (eventUi) {
            is EventUICharactersListWithDetails.OpenCharacterDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharactersListWithDetails.OpenCharacterDetailsScreen(eventUi.id))
                }
            }
            is EventUICharactersListWithDetails.OpenEpisodeDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharactersListWithDetails.OpenEpisodeDetailsScreen(eventUi.id))
                }
            }
            is EventUICharactersListWithDetails.OpenLocationDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharactersListWithDetails.OpenLocationDetailsScreen(eventUi.id))
                }
            }
            EventUICharactersListWithDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
        }

    }


    data class StateScreenViewModelCharactersListDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val characters: List<CharacterListWithDetailsData> = emptyList()
    )

    sealed class EventUICharactersListWithDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventUICharactersListWithDetails()
        data class OpenLocationDetailsScreen(val id: String) : EventUICharactersListWithDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventUICharactersListWithDetails()
        object Refresh : EventUICharactersListWithDetails()
    }

    sealed class EventViewModelCharactersListWithDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventViewModelCharactersListWithDetails()
        data class OpenLocationDetailsScreen(val id: String) : EventViewModelCharactersListWithDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventViewModelCharactersListWithDetails()
    }
}