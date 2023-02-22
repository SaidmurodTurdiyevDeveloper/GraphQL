package uz.graphql.ricky_and_morty_domen.model.location

import uz.graphql.LocationsWithIdsQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:17 PM for Rick And Morty GraphQL.
 */
data class LocationListWithDetailsData(
    val dimension: String,
    val name: String,
    val type: String,
    val created: String,
    val residents: List<LocationWithDetailsResidentData>
)

data class LocationWithDetailsResidentData(
    val id: String,
    val name: String,
    val status: String,
    val image: String,
    val created: String
)


fun LocationsWithIdsQuery.LocationsById.toLocation(): LocationListWithDetailsData {
    val newResidentsList = residents.map { data ->
        LocationWithDetailsResidentData(
            id = data?.id ?: "",
            name = data?.name ?: "",
            status = data?.status ?: "",
            image = data?.image ?: "",
            created = data?.created ?: ""
        )
    }
    return LocationListWithDetailsData(
        dimension = dimension ?: "",
        name = name ?: "",
        type = type ?: "",
        created = created ?: "",
        residents = newResidentsList
    )
}

fun List<LocationsWithIdsQuery.LocationsById?>.toLocationsList(): List<LocationListWithDetailsData> {
    return map { data ->
        data?.toLocation() ?: createEmptyLocation()
    }
}

private fun createEmptyLocation(): LocationListWithDetailsData {
    return LocationListWithDetailsData(
        dimension = "",
        name = "",
        type = "",
        created = "",
        residents = emptyList()
    )
}

