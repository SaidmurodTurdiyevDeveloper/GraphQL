package uz.graphql.ricky_and_morty_domen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.graphql.ricky_and_morty_data.repository.RepositoryCharacter
import uz.graphql.ricky_and_morty_data.repository.RepositoryEpisode
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation
import uz.graphql.ricky_and_morty_domen.use_cases.character.*
import uz.graphql.ricky_and_morty_domen.use_cases.episode.*
import uz.graphql.ricky_and_morty_domen.use_cases.location.*

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:03 PM for Rick And Morty GraphQL.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideCharacterUseCase(repositoryCharacter: RepositoryCharacter): UseCaseCharacter = UseCaseCharacter(
        getCharacter = GetCharacter(repositoryCharacter),
        getCharactersList = GetCharactersList(repositoryCharacter),
        getCharactersListWithFilter = GetCharactersListWithFilter(repositoryCharacter),
        getCharactersListWithIds = GetCharactersListWithIds(repositoryCharacter)
    )

    @Provides
    fun provideEpisodeUseCase(repositoryEpisode: RepositoryEpisode): UseCaseEpisode = UseCaseEpisode(
        getEpisode = GetEpisode(repositoryEpisode),
        getEpisodesList = GetEpisodesList(repositoryEpisode),
        getEpisodesListWithFilter = GetEpisodesListWithFilter(repositoryEpisode),
        getEpisodesListWithIds = GetEpisodesListWithIds(repositoryEpisode)
    )

    @Provides
    fun provideLocationUseCase(repositoryLocation: RepositoryLocation): UseCaseLocation = UseCaseLocation(
        getLocation = GetLocation(repositoryLocation),
        getLocationsList = GetLocationsList(repositoryLocation),
        getLocationsListWithFilter = GetLocationsListWithFilter(repositoryLocation),
        getLocationsListWithIds = GetLocationsListWithIds(repositoryLocation)
    )
}