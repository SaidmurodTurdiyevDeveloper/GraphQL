package uz.graphql.ricky_and_morty_data.repository

import uz.graphql.LocationListQuery
import uz.graphql.LocationListWithFilterQuery
import uz.graphql.LocationQuery
import uz.graphql.LocationsWithIdsQuery
import uz.graphql.common_utills.other.ResponseApi
import uz.graphql.ricky_and_morty_data.model.FilterLocationDTO

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryLocation {
    suspend fun getLocations(page:Int): ResponseApi<LocationListQuery.Locations>
    suspend fun getLocationsWithFilter(page: Int,filterData: FilterLocationDTO): ResponseApi<LocationListWithFilterQuery.Locations>
    suspend fun getLocation(id: String): ResponseApi<LocationQuery.Location>
    suspend fun getLocation(ls: List<String>): ResponseApi<List<LocationsWithIdsQuery.LocationsById?>>
}