package uz.graphql.common_utills.other

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 11/28/2022.
 */
sealed class ResponseData<T> {
    data class Loading<T>(val isLoading:Boolean) : ResponseData<T>()
    data class Error<T>(val message: String, val errorData: T? = null) : ResponseData<T>()
    data class Success<T>(val data: T) : ResponseData<T>()
}