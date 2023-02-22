package uz.graphql.ricky_and_morty_presenter.vieewModels.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.graphql.common_utills.other.ResponseData
import uz.graphql.ricky_and_morty_domen.model.character.CharacterListWithDetailsData
import uz.graphql.ricky_and_morty_domen.use_cases.character.UseCaseCharacter
import uz.graphql.ricky_and_morty_presenter.utils.MoveIdConstants
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/22/2023 3:37 PM for Ricky And Morty.
 */
@HiltViewModel
class CharactersListWithDetailsViewModel @Inject constructor(
    private val useCase: UseCaseCharacter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableState<ViewModelStateCharactersListDetailsScreen> = mutableStateOf(ViewModelStateCharactersListDetailsScreen())
    val state: State<ViewModelStateCharactersListDetailsScreen> get() = _state

    init {
        savedStateHandle.get<String>(MoveIdConstants.moveCharactersIdsList).let { json ->
            val ids = try {
                Gson().fromJson<List<String>>(json, String::class.java)
            } catch (e: Exception) {
                emptyList()
            }
            if (ids != null) {
                viewModelScope.launch {
                    if (ids.isEmpty())
                        _state.value = state.value.copy(error = "We cannot find any id")
                    else
                        useCase.getCharactersListWithIds(ids).collectLatest { response ->
                            when (response) {
                                is ResponseData.Error -> {
                                    _state.value = state.value.copy(error = response.message)
                                }
                                is ResponseData.Loading -> {
                                    _state.value = state.value.copy(isLoading = response.isLoading)
                                }
                                is ResponseData.Success -> {
                                    _state.value = state.value.copy(characters = response.data)
                                }
                            }
                        }
                }
            } else {
                _state.value = state.value.copy(error = "Gson parse error")
            }
        }
    }


    data class ViewModelStateCharactersListDetailsScreen(
        val isLoading: Boolean = false,
        val error: String? = null,
        val characters: List<CharacterListWithDetailsData> = emptyList()
    )
}