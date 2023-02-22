package uz.graphql.common_utills.other

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 11/28/2022.
 */
sealed class ResponseApi<T> {
    data class Error<T>(val message: String) : ResponseApi<T>()
    data class Success<T>(val data: T) : ResponseApi<T>()
}