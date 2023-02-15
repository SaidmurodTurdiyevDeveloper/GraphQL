package com.rick_and_morty.common_utills.other

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 11/28/2022.
 */
sealed class ResponseData<T> {
    class Loading<T>(var isLoading: Boolean) : ResponseData<T>()
    class Error<T>(var message: String) : ResponseData<T>()
    class Success<T>(var data: T) : ResponseData<T>()
}