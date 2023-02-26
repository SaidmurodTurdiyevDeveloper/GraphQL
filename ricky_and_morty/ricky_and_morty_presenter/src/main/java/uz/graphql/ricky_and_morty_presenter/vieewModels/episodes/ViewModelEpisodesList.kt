package uz.graphql.ricky_and_morty_presenter.vieewModels.episodes

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import uz.graphql.ricky_and_morty_domen.model.episode.EpisodesListData
import uz.graphql.ricky_and_morty_domen.use_cases.episode.UseCaseEpisode
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:40 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelEpisodesList @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseEpisode
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelEpisodesList> = mutableStateOf(StateScreenViewModelEpisodesList())
    val state: State<StateScreenViewModelEpisodesList> get() = _state

    private var filterName: String? = null
    private var filterEpisode: String? = null

    private val _event = MutableSharedFlow<EventViewModelEpisodesList>()
    val event get() = _event.asSharedFlow()

    private var selectedList = ArrayList<String>()

    init {
        loadWithNetworkNetwork(context, errorBlock = {
            _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
        }) {
            getEpisodes(0)
        }
    }

    private suspend fun getEpisodes(page: Int? = null) {
        viewModelScope.launch {
            if (filterName.isNullOrBlank() && filterEpisode.isNullOrBlank()) {
                useCase.getEpisodesList(
                    page = page
                )
            } else {
                useCase.getEpisodesListWithFilter(
                    page = page,
                    name = filterName,
                    episode = filterEpisode,
                )
            }.collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(episodes = state.value.episodes, error = null)
                    }
                }
            }
        }
    }

    fun onEvent(eventUi: EventUIEpisodesList) {
        when (eventUi) {
            is EventUIEpisodesList.Filter -> {
                filterEpisode = eventUi.episode
                filterName = eventUi.name
                viewModelScope.launch {
                    getEpisodes(0)
                }
            }
            EventUIEpisodesList.LoadList -> {
                filterEpisode = null
                filterName = null
                viewModelScope.launch {
                    getEpisodes(0)
                }
            }
            is EventUIEpisodesList.OpenEpisodeDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelEpisodesList.OpenEpisodeDetailsScreen(eventUi.id))
                }
            }
            is EventUIEpisodesList.SelectItem -> {
                if (isSelected(eventUi.id))
                    selectedList.remove(eventUi.id)
                else
                    selectedList.add(eventUi.id)
            }

            EventUIEpisodesList.LoadNextPage -> {
                viewModelScope.launch {
                    getEpisodes()
                }
            }
            EventUIEpisodesList.OpenEpisodesListWithDetailsScreen -> {
                viewModelScope.launch {
                    val idList = Gson().toJson(selectedList)
                    _event.emit(EventViewModelEpisodesList.OpenEpisodesListWithDetailsScreen(idList))
                }
            }
            EventUIEpisodesList.ClearSelectedItem -> {
                selectedList.clear()
                _state.value = state.value.copy()
            }
        }
    }

    fun isSelected(id: String): Boolean {
        if (selectedList.isEmpty())
            return false
        return selectedList.contains(id)
    }


    data class StateScreenViewModelEpisodesList(
        val isLoading: Boolean = false,
        val error: String? = null,
        val episodes: List<EpisodesListData> = emptyList()
    )

    sealed class EventUIEpisodesList {
        data class OpenEpisodeDetailsScreen(val id: String) : EventUIEpisodesList()

        data class Filter(
            val episode: String? = null,
            val name: String? = null
        ) : EventUIEpisodesList()

        data class SelectItem(val id: String) : EventUIEpisodesList()

        object ClearSelectedItem : EventUIEpisodesList()

        object LoadList : EventUIEpisodesList()

        object OpenEpisodesListWithDetailsScreen : EventUIEpisodesList()

        object LoadNextPage : EventUIEpisodesList()
    }

    sealed class EventViewModelEpisodesList {
        data class OpenEpisodeDetailsScreen(val id: String) : EventViewModelEpisodesList()

        data class OpenEpisodesListWithDetailsScreen(val idListGson: String) : EventViewModelEpisodesList()

        data class ShowToast(val message: String) : EventViewModelEpisodesList()

        data class ShowSnackBar(val message: String) : EventViewModelEpisodesList()
    }
}