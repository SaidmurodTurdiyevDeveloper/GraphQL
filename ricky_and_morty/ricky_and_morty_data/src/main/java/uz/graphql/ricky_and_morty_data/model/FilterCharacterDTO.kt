package uz.graphql.ricky_and_morty_data.model

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 2:12 PM for Rick And Morty GraphQL.
 */
data class FilterCharacterDTO(
    val gender: String? = null,
    val name: String? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
)
