package uz.graphql.ricky_and_morty_domen.model.location

import uz.graphql.LocationQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:17 PM for Rick And Morty GraphQL.
 */
data class LocationData(
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

fun LocationQuery.Location.toLocation(): LocationData {
    val newResidents = residents.map { data ->
        LocationResidentData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            status = data?.status ?: "",
            image = data?.image ?: "",
            created = data?.created ?: ""
        )
    }
    return LocationData(
        dimension = dimension ?: "",
        name = name ?: "",
        type = type ?: "",
        created = created ?: "",
        residents = newResidents
    )
}
