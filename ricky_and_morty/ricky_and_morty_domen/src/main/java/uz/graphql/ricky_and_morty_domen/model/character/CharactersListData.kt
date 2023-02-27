package uz.graphql.ricky_and_morty_domen.model.character


import uz.graphql.CharacterListQuery
import uz.graphql.CharacterListWithFilterQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 11:47 AM for Rick And Morty GraphQL.
 */
data class CharactersListData(
    val id: String,
    val image: String,
    val name: String,
    val status: String,
    val created: String,
    val species: String,
    val type: String,
    val gender: String,
    var select: Boolean = false
)

fun CharacterListQuery.Characters.toCharactersList(): List<CharactersListData> {
    this.results
    return this.results?.map { data ->
        CharactersListData(
            id = data?.id ?: "",
            image = data?.image ?: "",
            name = data?.name?.ifBlank { "-" } ?: "",
            status = data?.status?.ifBlank { "-" } ?: "",
            created = data?.created ?: "",
            species = data?.species?.ifBlank { "-" } ?: "",
            gender = data?.gender?.ifBlank { "-" } ?: "",
            type = data?.type?.ifBlank { "-" } ?: ""
        )
    } ?: emptyList()
}

fun CharacterListWithFilterQuery.Characters.toCharactersList(): List<CharactersListData> {
    this.results
    return this.results?.map { data ->
        CharactersListData(
            id = data?.id ?: "",
            image = data?.image ?: "",
            name = data?.name ?: "",
            status = data?.status ?: "",
            created = data?.created ?: "",
            species = data?.species ?: "",
            gender = data?.gender ?: "",
            type = data?.type ?: ""
        )
    } ?: emptyList()
}
