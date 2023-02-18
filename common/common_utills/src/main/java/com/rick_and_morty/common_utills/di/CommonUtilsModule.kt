package com.rick_and_morty.common_utills.di

import android.content.Context
import com.rick_and_morty.common_utills.dataStore.SharedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 5:04 PM for Rick And Morty GraphQL.
 */
@Module
@InstallIn(SingletonComponent::class)
object CommonUtilsModule {
    @Provides
    @Singleton
    fun provideSharedDatabase(@ApplicationContext context: Context): SharedDatabase {
        return SharedDatabase(context)
    }
}