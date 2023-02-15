package com.rick_and_morty.rickandmortygraphql.preseenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick_and_morty.common_utills.dataStore.SharedDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 11:59 AM for Rick And Morty GraphQL.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(private var store: SharedDatabase) : ViewModel() {
    private var _openHome = Channel<Boolean>()
    val openHome get() = _openHome.receiveAsFlow()

    private var _openStarting = Channel<Boolean>()
    val openStarting get() = _openStarting.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            if (store.firstEnter)
                _openHome.send(true)
            else
                _openStarting.send(true)
        }
    }
}