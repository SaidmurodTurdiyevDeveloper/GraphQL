package uz.graphql.rickyandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.rickyandmorty.navigator.NavigatorProvider
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