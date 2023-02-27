package uz.graphql.ricky_and_morty_presenter.vieewModels.episodes

import android.content.Context
import android.util.Log
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
import uz.graphql.ricky_and_morty_presenter.vieewModels.characters.ViewModelCharactersList
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
                        if (state.value.episodes.isEmpty()) {
                            _state.value = state.value.copy(isLoading = response.isLoading)
                        } else {
                            _state.value = state.value.copy(isLoadingItem = response.isLoading)
                        }                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(episodes = response.data, error = null)
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
                val selectCount = state.value.selectCount
                val newList = state.value.episodes.toMutableList().map {
                    if (it.id == eventUi.episode.id)
                        it.copy(select = eventUi.episode.select.not())
                    else
                        it
                }
                _state.value = state.value.copy(
                    episodes = newList,
                    selectCount = if (eventUi.episode.select)
                        selectCount - 1
                    else selectCount + 1
                )
            }

            EventUIEpisodesList.LoadNextPage -> {
                viewModelScope.launch {
                    getEpisodes()
                }
            }
            EventUIEpisodesList.OpenEpisodesListWithDetailsScreen -> {
                viewModelScope.launch {
                    val list = ArrayList<String>()
                    state.value.episodes.forEach {
                        if (it.select) {
                            list.add(it.id)
                            it.select = false
                        }
                    }
                    if (list.isEmpty())
                        _event.emit(EventViewModelEpisodesList.ShowToast("You have not any selected data"))
                    else {
                        val idList = Gson().toJson(list,List::class.java)
                        _event.emit(EventViewModelEpisodesList.OpenEpisodesListWithDetailsScreen(idList))
                    }
                    _state.value = _state.value.copy(selectCount = 0)
                }
            }
            EventUIEpisodesList.ClearSelectedItem -> {
                val newList = state.value.episodes.toMutableList().map {
                    it.copy(select = false)
                }
                _state.value = _state.value.copy(episodes = newList, selectCount = 0)
            }
        }
    }



    data class StateScreenViewModelEpisodesList(
        val isLoading: Boolean = false,
        val isLoadingItem: Boolean = false,
        val error: String? = null,
        val episodes: List<EpisodesListData> = emptyList(),
        val selectCount: Int = 0
    )

    sealed class EventUIEpisodesList {
        data class OpenEpisodeDetailsScreen(val id: String) : EventUIEpisodesList()

        data class Filter(
            val episode: String? = null,
            val name: String? = null
        ) : EventUIEpisodesList()

        data class SelectItem(val episode: EpisodesListData) : EventUIEpisodesList()

        object LoadList : EventUIEpisodesList()

        object OpenEpisodesListWithDetailsScreen : EventUIEpisodesList()

        object LoadNextPage : EventUIEpisodesList()
        object ClearSelectedItem : EventUIEpisodesList()

    }

    sealed class EventViewModelEpisodesList {
        data class OpenEpisodeDetailsScreen(val id: String) : EventViewModelEpisodesList()

        data class OpenEpisodesListWithDetailsScreen(val idList: String) : EventViewModelEpisodesList()

        data class ShowToast(val message: String) : EventViewModelEpisodesList()

        data class ShowSnackBar(val message: String) : EventViewModelEpisodesList()
    }
}