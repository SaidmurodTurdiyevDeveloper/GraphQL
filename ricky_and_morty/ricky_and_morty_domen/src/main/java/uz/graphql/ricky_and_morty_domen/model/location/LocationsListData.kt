package uz.graphql.ricky_and_morty_domen.model.location


import uz.graphql.LocationListQuery
import uz.graphql.LocationListWithFilterQuery

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:18 PM for Rick And Morty GraphQL.
 */
data class LocationsListData(
    val id:String,
    val dimension:String,
    val name:String,
    val type:String,
    val created:String
)


fun LocationListQuery.Locations.toLocationsList(): List<LocationsListData> {
    return this.results?.map { data ->
        LocationsListData(
            id = data?.id ?: "",
            dimension = data?.dimension ?: "",
            name = data?.name ?: "",
            type=data?.type?:"",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}

fun LocationListWithFilterQuery.Locations.toLocationsList(): List<LocationsListData> {
    return this.results?.map { data ->
        LocationsListData(
            id = data?.id ?: "",
            dimension = data?.dimension ?: "",
            name = data?.name ?: "",
            type=data?.type?:"",
            created = data?.created ?: ""
        )
    } ?: emptyList()
}

