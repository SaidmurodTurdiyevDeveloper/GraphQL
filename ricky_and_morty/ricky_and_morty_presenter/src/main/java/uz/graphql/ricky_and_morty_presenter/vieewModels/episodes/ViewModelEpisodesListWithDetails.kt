package uz.graphql.ricky_and_morty_presenter.vieewModels.episodes

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
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodeListWithDetailsData
import uz.graphql.ricky_and_morty_domen.use_cases.episode.UseCaseEpisode
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:40 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelEpisodesListWithDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseEpisode,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelEpisodesListDetails> = mutableStateOf(StateScreenViewModelEpisodesListDetails())
    val state: State<StateScreenViewModelEpisodesListDetails> get() = _state
    private var idList = emptyList<String>()

    private val _event = MutableSharedFlow<EventViewModelEpisodesListWithDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveEpisodesIdsList).let { json ->
            idList = try {
                Gson().fromJson<List<String>>(json, List::class.java)
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
            useCase.getEpisodesListWithIds(idList).collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(episodes = response.data, error = null)
                    }
                }
            }
    }
    fun onEvent(eventUi: EventUIEpisodesListWithDetails) {
        when (eventUi) {
            is EventUIEpisodesListWithDetails.OpenCharacterDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelEpisodesListWithDetails.OpenCharacterDetailsScreen(eventUi.id))
                }
            }
            EventUIEpisodesListWithDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
            is EventUIEpisodesListWithDetails.OpenEpisodeDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelEpisodesListWithDetails.OpenEpisodeDetailsScreen(eventUi.id))
                }
            }
        }
    }


    data class StateScreenViewModelEpisodesListDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val episodes: List<EpisodeListWithDetailsData> = emptyList()
    )

    sealed class EventUIEpisodesListWithDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventUIEpisodesListWithDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventUIEpisodesListWithDetails()
        object Refresh : EventUIEpisodesListWithDetails()
    }

    sealed class EventViewModelEpisodesListWithDetails {
        data class OpenCharacterDetailsScreen(val id: String) : EventViewModelEpisodesListWithDetails()
        data class OpenEpisodeDetailsScreen(val id: String) : EventViewModelEpisodesListWithDetails()
    }
}