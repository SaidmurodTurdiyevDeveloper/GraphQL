package uz.graphql.ricky_and_morty_domen.model.location

import uz.graphql.LocationQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:17 PM for Rick And Morty GraphQL.
 */
data class LocationDetailsData(
    val dimension: String,
    val name: String,
    val type: String,
    val created: String,
    val residents: List<LocationResidentData>
)

data class LocationResidentData(
    val id: String,
    val name: String,
    val status: String,
    val image: String,
    val created: String
)

fun LocationQuery.Location.toLocation(): LocationDetailsData {
    val newResidents = residents.map { data ->
        LocationResidentData(
            id = data?.id ?: "",
            name = data?.name?.ifBlank { "-" } ?: "",
            status = data?.status?.ifBlank { "-" } ?: "",
            image = data?.image ?: "",
            created = data?.created ?: ""
        )
    }

    return LocationDetailsData(
        dimension = dimension ?: "",
        name = name?.ifBlank { "-" } ?: "",
        type = type?.ifBlank { "-" } ?: "",
        created = created ?: "",
        residents = newResidents
    )
}
