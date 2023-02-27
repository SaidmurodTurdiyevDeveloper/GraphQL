package uz.graphql.ricky_and_morty_presenter.vieewModels.locations

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
import uz.graphql.ricky_and_morty_domen.model.location.LocationsListData
import uz.graphql.ricky_and_morty_domen.use_cases.location.UseCaseLocation
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import uz.graphql.ricky_and_morty_presenter.vieewModels.episodes.ViewModelEpisodesList
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:45 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelLocationsList @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseLocation
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelLocationsList> = mutableStateOf(StateScreenViewModelLocationsList())
    val state: State<StateScreenViewModelLocationsList> get() = _state

    private var filterName: String? = null
    private var filterDimension: String? = null
    private var filterType: String? = null

    private val _event = MutableSharedFlow<EventViewModelLocationsList>()
    val event get() = _event.asSharedFlow()

    init {
        loadWithNetworkNetwork(context, errorBlock = {
            _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
        }) {
            getLocations()
        }
    }

    private suspend fun getLocations(page: Int? = null) {
        viewModelScope.launch {
            if (filterName.isNullOrBlank() &&
                filterDimension.isNullOrBlank() &&
                filterType.isNullOrBlank()
            ) {
                useCase.getLocationsList(page = page)
            } else {
                useCase.getLocationsListWithFilter(
                    page = page,
                    dimension = filterDimension,
                    name = filterName,
                    type = filterType
                )
            }.collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        if (state.value.locations.isEmpty()) {
                            _state.value = state.value.copy(isLoading = response.isLoading)
                        } else {
                            _state.value = state.value.copy(isLoadingItem = response.isLoading)
                        }                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(locations = response.data, error = null)
                    }
                }
            }
        }
    }

    fun onEvent(eventUi: EventUILocationsList) {
        when (eventUi) {
            is EventUILocationsList.Filter -> {
                filterDimension = eventUi.dimension
                filterName = eventUi.name
                filterType = eventUi.type
                viewModelScope.launch {
                    getLocations()
                }
            }
            EventUILocationsList.LoadList -> {
                filterDimension = null
                filterName = null
                filterType = null
                viewModelScope.launch {
                    getLocations()
                }
            }
            is EventUILocationsList.OpenLocationDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelLocationsList.OpenLocationDetailsScreen(eventUi.id))
                }
            }
            is EventUILocationsList.SelectItem -> {
                val selectCount = state.value.selectCount
                val newList = state.value.locations.toMutableList().map {
                    if (it.id == eventUi.location.id)
                        it.copy(select = eventUi.location.select.not())
                    else
                        it
                }
                _state.value = state.value.copy(
                    locations = newList,
                    selectCount = if (eventUi.location.select)
                        selectCount - 1
                    else selectCount + 1
                )
            }

            EventUILocationsList.LoadNextPage -> {
                viewModelScope.launch {
                    getLocations()
                }
            }
            EventUILocationsList.OpenLocationsListWithDetailsScreen -> {
                viewModelScope.launch {
                    val list = ArrayList<String>()
                    state.value.locations.forEach {
                        if (it.select) {
                            list.add(it.id)
                            it.select = false
                        }
                    }
                    if (list.isEmpty())
                        _event.emit(EventViewModelLocationsList.ShowToast("You have not any selected data"))
                    else {
                        val idList = Gson().toJson(list, List::class.java)
                        _event.emit(EventViewModelLocationsList.OpenLocationsListWithDetailsScreen(idList))
                    }
                    _state.value = _state.value.copy(selectCount = 0)
                }
            }
            EventUILocationsList.ClearSelectedItem -> {
                val newList = state.value.locations.toMutableList().map {
                    it.copy(select = false)
                }
                _state.value = _state.value.copy(locations = newList, selectCount = 0)
            }

        }
    }


    data class StateScreenViewModelLocationsList(
        val isLoading: Boolean = false,
        val isLoadingItem: Boolean = false,
        val error: String? = null,
        val locations: List<LocationsListData> = emptyList(),
        val selectCount: Int = 0
    )

    sealed class EventUILocationsList {
        data class OpenLocationDetailsScreen(val id: String) : EventUILocationsList()

        data class Filter(
            val dimension: String? = null,
            val name: String? = null,
            val type: String? = null
        ) : EventUILocationsList()

        data class SelectItem(val location: LocationsListData) : EventUILocationsList()

        object LoadList : EventUILocationsList()

        object OpenLocationsListWithDetailsScreen : EventUILocationsList()
        object ClearSelectedItem : EventUILocationsList()

        object LoadNextPage : EventUILocationsList()
    }

    sealed class EventViewModelLocationsList {
        data class OpenLocationDetailsScreen(val id: String) : EventViewModelLocationsList()

        data class OpenLocationsListWithDetailsScreen(val idListGson: String) : EventViewModelLocationsList()

        data class ShowToast(val message: String) : EventViewModelLocationsList()

        data class ShowSnackBar(val message: String) : EventViewModelLocationsList()
    }
}