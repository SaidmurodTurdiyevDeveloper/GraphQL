package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.character.CharacterData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private var useCase: UseCaseCharacter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<ViewModelStateCharacterDetailsScreen> = mutableStateOf(ViewModelStateCharacterDetailsScreen())
    val state: State<ViewModelStateCharacterDetailsScreen> get() = _state

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveCharacterId).let { id ->
            if (id != null) {
                viewModelScope.launch {
                    useCase.getCharacter(id).collectLatest { response ->
                        when (response) {
                            is ResponseData.Error -> {
                                _state.value = state.value.copy(error = response.message)
                            }
                            is ResponseData.Loading -> {
                                _state.value = state.value.copy(isLoading = response.isLoading)
                            }
                            is ResponseData.Success -> {
                                _state.value = state.value.copy(character = response.data)
                            }
                        }
                    }
                }
            } else {
                _state.value = state.value.copy(error = "Id could not find")
            }
        }
    }

    data class ViewModelStateCharacterDetailsScreen(
        val isLoading: Boolean = false,
        val error: String? = null,
        val character: CharacterData? = null
    )
}