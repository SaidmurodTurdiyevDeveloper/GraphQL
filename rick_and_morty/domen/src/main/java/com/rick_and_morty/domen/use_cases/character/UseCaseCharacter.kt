package com.rick_and_morty.domen.use_cases.character

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:01 PM for Rick And Morty GraphQL.
 */
data class UseCaseCharacter(
    val getCharacter: GetCharacter,
    val getCharactersList: GetCharactersList,
    val getCharactersListWithFilter: GetCharactersListWithFilter,
    val getCharactersListWithIds: GetCharactersListWithIds
)
