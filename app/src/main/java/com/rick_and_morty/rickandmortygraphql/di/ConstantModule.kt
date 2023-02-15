package com.rick_and_morty.rickandmortygraphql.di

import com.rick_and_morty.common_utills.navigator.Navigator
import com.rick_and_morty.rickandmortygraphql.navigator.NavigatorProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 6:20 PM for Rick And Morty GraphQL.
 */
@InstallIn(SingletonComponent::class)
@Module
object ConstantModule {
    @Provides
    @Singleton
    fun provideNavigateProvider(): Navigator.Provider {
        return NavigatorProvider()
    }
}