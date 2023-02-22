package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.character.CharactersListData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val useCase: UseCaseCharacter
) : ViewModel() {
    private val _state: MutableState<ViewModelStateCharactersScreen> = mutableStateOf(ViewModelStateCharactersScreen())
    val state: State<ViewModelStateCharactersScreen> get() = _state
    private var currentPage = -1

    init {
        getCharacters(0)
    }

    fun getCharacters(page: Int) {
        if (currentPage == page || currentPage > page)
            return
        viewModelScope.launch {
            useCase.getCharactersList(page).collectLatest { response ->
                when (response) {
                    is ResponseData.Error -> {
                        _state.value = state.value.copy(error = response.message)
                    }
                    is ResponseData.Loading -> {
                        _state.value = state.value.copy(isLoading = response.isLoading)
                    }
                    is ResponseData.Success -> {
                        val list = ArrayList(state.value.characters)
                        list.addAll(response.data)
                        currentPage = page
                        _state.value = state.value.copy(characters = list)
                    }
                }
            }
        }

    }

    data class ViewModelStateCharactersScreen(
        val isLoading: Boolean = false,
        val error: String? = null,
        val characters: List<CharactersListData> = emptyList()
    )
}