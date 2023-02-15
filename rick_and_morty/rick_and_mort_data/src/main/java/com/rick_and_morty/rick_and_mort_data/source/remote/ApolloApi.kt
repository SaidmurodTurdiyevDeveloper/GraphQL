package com.rick_and_morty.rick_and_mort_data.source.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.rick_and_morty.rick_and_mort_data.BuildConfig

import okhttp3.OkHttpClient

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 9:04 PM for Rick And Morty GraphQL.
 */
class ApolloApi {
    fun getApolloClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}