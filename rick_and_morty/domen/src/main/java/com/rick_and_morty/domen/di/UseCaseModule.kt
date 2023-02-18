package com.rick_and_morty.domen.di

import com.rick_and_morty.domen.use_cases.character.*
import com.rick_and_morty.domen.use_cases.episode.*
import com.rick_and_morty.domen.use_cases.location.*
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryEpisode
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryLocation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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