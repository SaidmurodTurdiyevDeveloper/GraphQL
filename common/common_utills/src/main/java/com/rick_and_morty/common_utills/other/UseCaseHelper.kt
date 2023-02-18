package com.rick_and_morty.common_utills.other

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 1:22 PM for Rick And Morty GraphQL.
 */

fun <T, K, S> invokeUseCase(requireData: S, resultBlock: suspend (S) -> ResponseApi<K>, successBlock: (K) -> T): Flow<ResponseData<T>> = flow {
    emit(ResponseData.Loading(true))
    when (val result = resultBlock(requireData)) {
        is ResponseApi.Success -> {
            emit(ResponseData.Success(successBlock(result.data)))
            emit(ResponseData.Loading(false))
        }
        is ResponseApi.Error -> {
            emit(ResponseData.Error(result.message))
            emit(ResponseData.Loading(false))
        }
    }
}

fun <T, K> invokeUseCase(resultBlock: suspend () -> ResponseApi<K>, successBlock: (K) -> T): Flow<ResponseData<T>> = flow {
    emit(ResponseData.Loading(true))
    when (val result = resultBlock()) {
        is ResponseApi.Success -> {
            emit(ResponseData.Success(successBlock(result.data)))
            emit(ResponseData.Loading(false))
        }
        is ResponseApi.Error -> {
            emit(ResponseData.Error(result.message))
            emit(ResponseData.Loading(false))
        }
    }
}

fun <T, K, S, H> invokeUseCase(requireFirstData: S, requireSecondData: H, resultBlock: suspend (S, H) -> ResponseApi<K>, successBlock: (K) -> T): Flow<ResponseData<T>> = flow {
    emit(ResponseData.Loading(true))
    when (val result = resultBlock(requireFirstData, requireSecondData)) {
        is ResponseApi.Success -> {
            emit(ResponseData.Success(successBlock(result.data)))
            emit(ResponseData.Loading(false))
        }
        is ResponseApi.Error -> {
            emit(ResponseData.Error(result.message))
            emit(ResponseData.Loading(false))
        }
    }
}

fun <T, K, S, H, M> invokeUseCase(requireFirstData: S, requireSecondData: H, requireThirdData: M, resultBlock: suspend (S, H, M) -> ResponseApi<K>, successBlock: (K) -> T): Flow<ResponseData<T>> =
    flow {
        emit(ResponseData.Loading(true))
        when (val result = resultBlock(requireFirstData, requireSecondData, requireThirdData)) {
            is ResponseApi.Success -> {
                emit(ResponseData.Success(successBlock(result.data)))
                emit(ResponseData.Loading(false))
            }
            is ResponseApi.Error -> {
                emit(ResponseData.Error(result.message))
                emit(ResponseData.Loading(false))
            }
        }
    }