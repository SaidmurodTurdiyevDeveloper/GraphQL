package com.rick_and_morty.rick_and_mort_data.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.rick_and_morty.common_utills.other.Constants
import com.rick_and_morty.rick_and_mort_data.source.remote.ApolloApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:56 PM for Rick And Morty GraphQL.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideApolloApi(): ApolloApi = ApolloApi()

//    @Provides
//    @Singleton
//    fun provideApolloAClient(api: ApolloApi): ApolloClient = api.getApolloClient()
}