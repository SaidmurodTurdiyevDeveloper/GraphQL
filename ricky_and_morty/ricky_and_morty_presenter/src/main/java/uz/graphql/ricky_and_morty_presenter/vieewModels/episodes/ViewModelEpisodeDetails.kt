package uz.graphql.ricky_and_morty_presenter.vieewModels.episodes

import android.content.Context
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
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeDetailsData
import uz.graphql.ricky_and_morty_domen.use_cases.episode.UseCaseEpisode
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharacterDetails
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:40 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelEpisodeDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseEpisode,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelEpisodeDetails> = mutableStateOf(StateScreenViewModelEpisodeDetails())
    val state: State<StateScreenViewModelEpisodeDetails> get() = _state

    private var id = ""

    private val _event = MutableSharedFlow<EventViewModelEpisodeDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveEpisodeId).let { newId ->
            if (newId != null) {
                id=newId
                loadWithNetworkNetwork(
                    context,
                    errorBlock = {
                        _state.value = state.value.copy(error = "Id could not find")
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
        useCase.getEpisode(id).collectLatest { response ->
            when (response) {
                is ResponseData.Error -> {
                    _state.value = state.value.copy(error = response.message)
                }
                is ResponseData.Loading -> {
                    _state.value = state.value.copy(isLoading = response.isLoading)
                }
                is ResponseData.Success -> {
                    _state.value = state.value.copy(
                        episode = response.data,
                        error = null
                    )
                }
            }
        }

    }

    fun onEvent(eventUi: EventUIEpisodeDetails) {
        when (eventUi) {
            is EventUIEpisodeDetails.OpenCharacterDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelEpisodeDetails.OpenCharacterDetailsScreen(eventUi.id))
                }
            }
            EventUIEpisodeDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
        }
    }


    data class StateScreenViewModelEpisodeDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val episode: EpisodeDetailsData? = null
    )

    sealed class EventUIEpisodeDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventUIEpisodeDetails()
        object Refresh : EventUIEpisodeDetails()
    }

    sealed class EventViewModelEpisodeDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventViewModelEpisodeDetails()
    }
}