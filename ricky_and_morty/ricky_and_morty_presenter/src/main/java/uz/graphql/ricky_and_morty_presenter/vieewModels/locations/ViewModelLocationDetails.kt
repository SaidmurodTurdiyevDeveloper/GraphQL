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
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.location.LocationDetailsData
import uz.graphql.ricky_and_morty_domen.model.location.LocationResidentData
import uz.graphql.ricky_and_morty_domen.use_cases.location.UseCaseLocation
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:46 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelLocationDetails @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseLocation,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<StateScreenViewModelLocationDetails> = mutableStateOf(StateScreenViewModelLocationDetails())
    val state: State<StateScreenViewModelLocationDetails> get() = _state
    private var id = ""

    private val _event = MutableSharedFlow<EventViewModelLocationDetails>()
    val event get() = _event.asSharedFlow()

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveLocationId).let { newId ->
            if (newId != null) {
                id = newId
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
        useCase.getLocation(id).collectLatest { response ->
            when (response) {
                is ResponseData.Error -> {
                    _state.value = state.value.copy(error = response.message)
                }
                is ResponseData.Loading -> {
                    _state.value = state.value.copy(isLoading = response.isLoading)
                }
                is ResponseData.Success -> {
                    _state.value = state.value.copy(location = response.data)
                }
            }
        }
    }

    fun onEvent(eventUi: EventUILocationDetails) {
        when (eventUi) {
            EventUILocationDetails.Refresh -> {
                viewModelScope.launch {
                    load()
                }
            }
            is EventUILocationDetails.OpenResidents -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelLocationDetails.OpenResidents(eventUi.id))
                }
            }
        }
    }


    data class StateScreenViewModelLocationDetails(
        val isLoading: Boolean = false,
        val error: String? = null,
        val location: LocationDetailsData? = null
    )

    sealed class EventUILocationDetails {
        object Refresh : EventUILocationDetails()
        data class OpenResidents(val id: String) : EventUILocationDetails()
    }

    sealed class EventViewModelLocationDetails {
        data class OpenResidents(val id: String) : EventViewModelLocationDetails()
    }
}