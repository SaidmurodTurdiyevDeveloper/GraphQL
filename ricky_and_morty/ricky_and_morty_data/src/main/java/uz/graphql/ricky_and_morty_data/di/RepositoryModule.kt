package uz.graphql.ricky_and_morty_data.di

import uz.graphql.ricky_and_morty_data.repository.RepositoryCharacter
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation
import uz.graphql.ricky_and_morty_data.repository.impl.RepositoryCharacterImpl
import uz.graphql.ricky_and_morty_data.repository.impl.RepositoryEpisodeImpl
import uz.graphql.ricky_and_morty_data.repository.impl.RepositoryLocationImpl
import com.rick_and_morty.rick_and_mort_data.source.remote.ApolloApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:39 PM for Rick And Morty GraphQL.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(api: ApolloApi): RepositoryCharacter = RepositoryCharacterImpl(api.getApolloClient())

    @Provides
    @Singleton
    fun provideEpisodeRepository(api: ApolloApi): RepositoryEpisode = RepositoryEpisodeImpl(api.getApolloClient())

    @Provides
    @Singleton
    fun provideLocationRepository(api: ApolloApi): RepositoryLocation = RepositoryLocationImpl(api.getApolloClient())
}