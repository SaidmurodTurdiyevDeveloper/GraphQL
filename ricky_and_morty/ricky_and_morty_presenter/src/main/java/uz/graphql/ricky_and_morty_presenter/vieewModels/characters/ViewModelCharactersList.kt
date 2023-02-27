package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

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

    private val _event = MutableSharedFlow<EventViewModelCharactersList>()
    val event get() = _event.asSharedFlow()


    init {
        loadWithNetworkNetwork(context, errorBlock = {
            _state.value = state.value.copy(error = Constants.ERROR_NETWORK)
        }) {
            getCharacters(0)
        }
    }

    private suspend fun getCharacters(page: Int? = null) {
        if (filterGender.isNullOrBlank()
            && filterName.isNullOrBlank()
            && filterSpecies.isNullOrBlank()
            && filterStatus.isNullOrBlank()
            && filterType.isNullOrBlank()
        ) {
            useCase.getCharactersList(page = page)
        } else {
            useCase.getCharactersListWithFilter(
                page = page,
                gender = filterGender,
                name = filterName,
                species = filterSpecies,
                status = filterStatus,
                type = filterType
            )
        }.collectLatest { response ->
            when (response) {
                is ResponseData.Error -> {
                    if (state.value.characters.isEmpty()) {
                        _state.value = state.value.copy(error = response.message)
                    } else {
                        _event.emit(EventViewModelCharactersList.ShowSnackBar(response.message))
                    }
                }
                is ResponseData.Loading -> {
                    if (state.value.characters.isEmpty()) {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    } else {
                        _state.value = state.value.copy(isLoadingItem = response.isLoading)
                    }
                }
                is ResponseData.Success -> {
                    _state.value = state.value.copy(characters = ArrayList(response.data), error = null, selectCount = 0)
                }
            }
        }

    }

    fun onEvent(eventUi: EventUICharactersList) {
        when (eventUi) {
            is EventUICharactersList.Filter -> {
                viewModelScope.launch {
                    filterGender = eventUi.gender
                    filterName = eventUi.name
                    filterSpecies = eventUi.species
                    filterStatus = eventUi.status
                    filterType = eventUi.type
                    getCharacters(0)
                }
            }
            is EventUICharactersList.OpenCharacterDetailsScreen -> {
                viewModelScope.launch {
                    if (eventUi.id.trim().isBlank()) {
                        _event.emit(EventViewModelCharactersList.ShowToast("You selected item id cannot find please try again"))
                    } else
                        _event.emit(EventViewModelCharactersList.OpenCharacterDetailsScreen(eventUi.id))
                }
            }
            is EventUICharactersList.SelectItem -> {
                val selectCount = state.value.selectCount
                val newList = state.value.characters.toMutableList().map {
                    if (it.id == eventUi.character.id)
                        it.copy(select = eventUi.character.select.not())
                    else
                        it
                }
                _state.value = state.value.copy(
                    characters = newList,
                    selectCount = if (eventUi.character.select)
                        selectCount - 1
                    else selectCount + 1
                )
            }
            EventUICharactersList.LoadList -> {
                filterGender = null
                filterName = null
                filterSpecies = null
                filterStatus = null
                filterType = null
                viewModelScope.launch {
                    getCharacters(0)
                }
            }

            EventUICharactersList.LoadNextPage -> {
                viewModelScope.launch {
                    getCharacters()
                }
            }
            EventUICharactersList.ClearSelectedItem -> {
                val newList = state.value.characters.toMutableList().map {
                    it.copy(select = false)
                }
                _state.value = _state.value.copy(characters = newList, selectCount = 0)
            }
            is EventUICharactersList.OpenCharactersListWithDetailsScreen -> {
                viewModelScope.launch {
                    val list = ArrayList<String>()
                    state.value.characters.forEach {
                        if (it.select) {
                            list.add(it.id)
                            it.select = false
                        }
                    }
                    if (list.isEmpty())
                        _event.emit(EventViewModelCharactersList.ShowToast("You have not any selected data"))
                    else {
                        val idList = Gson().toJson(list,List::class.java)
                        _event.emit(EventViewModelCharactersList.OpenCharactersListWithDetailsScreen(idList))
                    }
                    _state.value = _state.value.copy(selectCount = 0)
                }
            }
        }
    }


    data class StateScreenOfViewModelCharactersList(
        val isLoading: Boolean = false,
        val isLoadingItem: Boolean = false,
        val error: String? = null,
        val characters: List<CharactersListData> = emptyList(),
        val selectCount: Int = 0
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

        data class SelectItem(val character: CharactersListData) : EventUICharactersList()
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