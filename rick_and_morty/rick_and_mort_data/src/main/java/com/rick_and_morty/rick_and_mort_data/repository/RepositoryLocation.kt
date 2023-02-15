package com.rick_and_morty.rick_and_mort_data.repository

import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.data_graphql.type.FilterLocation

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:43 PM for Rick And Morty GraphQL.
 */
interface RepositoryLocation {
    suspend fun getLocations(page:Int): ResponseData<LocationListQuery.Locations>
    suspend fun getLocationsWithFilter(page: Int,filter: FilterLocation): ResponseData<LocationListWithFilterQuery.Locations>
    suspend fun getLocation(id: String): ResponseData<LocationQuery.Location>
    suspend fun getLocation(ls: List<String>): ResponseData<List<LocationsWithIdsQuery.LocationsById?>>
}