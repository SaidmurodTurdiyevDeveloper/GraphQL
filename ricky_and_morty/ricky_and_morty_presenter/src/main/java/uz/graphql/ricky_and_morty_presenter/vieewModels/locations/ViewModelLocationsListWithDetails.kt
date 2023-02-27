package uz.graphql.ricky_and_morty_presenter.vieewModels.locations

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
import uz.graphql.ricky_and_morty_domen.model.location.LocationListWithDetailsData
import uz.graphql.ricky_and_morty_domen.model.location.LocationResidentData
import uz.graphql.ricky_and_morty_domen.use_cases.location.UseCaseLocation
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:46 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelLocationsListWithDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseLocation,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<StateScreenViewModelLocationsListDetails> = mutableStateOf(StateScreenViewModelLocationsListDetails())
    val state: State<StateScreenViewModelLocationsListDetails> get() = _state

    private var idList = emptyList<String>()

    private val _event = MutableSharedFlow<EventViewModelLocationsListWithDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveLocationsIdsList).let { json ->
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
            useCase.getLocationsListWithIds(idList).collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    }
                    is ResponseData.Success -> {
                        _state.value = state.value.copy(locations = response.data)
                    }
                }
            }
    }

    fun onEvent(eventUi: EventUILocationsListWithDetails) {
        when (eventUi) {
            EventUILocationsListWithDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
            is EventUILocationsListWithDetails.OpenResidents -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelLocationsListWithDetails.OpenResidents(eventUi.id))
                }
            }
            is EventUILocationsListWithDetails.OpenLocation -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelLocationsListWithDetails.OpenLocation(eventUi.id))
                }
            }
        }
    }

    data class StateScreenViewModelLocationsListDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val locations: List<LocationListWithDetailsData> = emptyList()
    )

    sealed class EventUILocationsListWithDetails {
        object Refresh : EventUILocationsListWithDetails()
        data class OpenResidents(val id: String) : EventUILocationsListWithDetails()
        data class OpenLocation(val id: String) : EventUILocationsListWithDetails()

    }



    sealed class EventViewModelLocationsListWithDetails {
        data class OpenResidents(val id: String) : EventViewModelLocationsListWithDetails()
        data class OpenLocation(val id: String) : EventViewModelLocationsListWithDetails()
    }
}