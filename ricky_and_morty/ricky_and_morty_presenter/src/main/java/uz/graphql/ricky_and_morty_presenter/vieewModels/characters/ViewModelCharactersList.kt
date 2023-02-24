package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

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
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import uz.graphql.ricky_and_morty_presenter.utils.loadWithNetworkNetwork
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class ViewModelCharactersList @Inject constructor(
    @ApplicationContext
    context: Context,
    private val useCase: UseCaseCharacter
) : ViewModel() {
    private val _state: MutableState<StateScreenOfViewModelCharactersList> = mutableStateOf(StateScreenOfViewModelCharactersList())
    val state: State<StateScreenOfViewModelCharactersList> get() = _state

    private var filterGender: String? = null
    private var filterName: String? = null
    private var filterSpecies: String? = null
    private var filterStatus: String? = null
    private var filterType: String? = null

    private var selectedList = ArrayList<String>()

    private val _event = MutableSharedFlow<EventViewModelCharactersList>()
    val event get() = _event.asSharedFlow()

    init {
        loadWithNetworkNetwork(context, errorBlock = {
            _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
        }) {
            getCharacters()
        }
    }

    private suspend fun getCharacters() {
        if (filterGender.isNullOrBlank()
            && filterName.isNullOrBlank()
            && filterSpecies.isNullOrBlank()
            && filterStatus.isNullOrBlank()
            && filterType.isNullOrBlank()
        ) {
            useCase.getCharactersList()
        } else {
            useCase.getCharactersListWithFilter(
                gender = filterGender,
                name = filterName,
                species = filterSpecies,
                status = filterStatus,
                type = filterType
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
                    _state.value = state.value.copy(characters = state.value.characters, error = null)
                }
            }
        }

    }

    fun onEvent(eventUi: EventUICharactersList) {
        when (eventUi) {
            is EventUICharactersList.Filter -> {
                filterGender = eventUi.gender
                filterName = eventUi.name
                filterSpecies = eventUi.species
                filterStatus = eventUi.status
                filterType = eventUi.type
                viewModelScope.launch {
                    getCharacters()
                }
            }
            is EventUICharactersList.OpenCharacterDetailsScreen -> {
                viewModelScope.launch {
                    _event.emit(EventViewModelCharactersList.OpenCharacterDetailsScreen(eventUi.id))
                }
            }
            is EventUICharactersList.SelectItem -> {
                if (isSelected(eventUi.id))
                    selectedList.remove(eventUi.id)
                else
                    selectedList.add(eventUi.id)
            }
            EventUICharactersList.LoadList -> {
                filterGender = null
                filterName = null
                filterSpecies = null
                filterStatus = null
                filterType = null
                viewModelScope.launch {
                    getCharacters()
                }
            }

            EventUICharactersList.LoadNextPage -> {
                viewModelScope.launch {
                    getCharacters()
                }
            }
            EventUICharactersList.ClearSelectedItem -> {
                selectedList.clear()
                _state.value = state.value.copy()
            }
            EventUICharactersList.OpenCharactersListWithDetailsScreen -> {
                viewModelScope.launch {
                    val idList = Gson().toJson(selectedList)
                    _event.emit(EventViewModelCharactersList.OpenCharactersListWithDetailsScreen(idList))
                }
            }
        }
    }

    fun isSelected(id: String): Boolean {
        if (selectedList.isEmpty())
            return false
        return selectedList.contains(id)
    }

    data class StateScreenOfViewModelCharactersList(
        val isLoading: Boolean = false,
        val error: String? = null,
        val characters: List<CharactersListData> = emptyList()
    )

    sealed class EventUICharactersList {
        data class OpenCharacterDetailsScreen(val id: String) : EventUICharactersList()
        data class Filter(
            val gender: String? = null,
            val name: String? = null,
            val species: String? = null,
            val status: String? = null,
            val type: String? = null
        ) : EventUICharactersList()

        data class SelectItem(val id: String) : EventUICharactersList()

        object OpenCharactersListWithDetailsScreen : EventUICharactersList()
        object ClearSelectedItem : EventUICharactersList()
        object LoadList : EventUICharactersList()
        object LoadNextPage : EventUICharactersList()
    }

    sealed class EventViewModelCharactersList {
        data class OpenCharacterDetailsScreen(val id: String) : EventViewModelCharactersList()
        data class OpenCharactersListWithDetailsScreen(val idsListGson: String) : EventViewModelCharactersList()

        data class ShowToast(val message: String) : EventViewModelCharactersList()

        data class ShowSnackBar(val message: String) : EventViewModelCharactersList()
    }
}