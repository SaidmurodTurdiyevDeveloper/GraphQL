package com.rick_and_morty.rick_and_mort_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.rick_and_morty.common_utills.other.ResponseData
import com.rick_and_morty.data_graphql.*
import com.rick_and_morty.data_graphql.type.FilterCharacter
import com.rick_and_morty.data_graphql.type.FilterLocation
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryCharacter
import com.rick_and_morty.rick_and_mort_data.repository.RepositoryLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryLocationImpl(private var client: ApolloClient) : RepositoryLocation {
    override suspend fun getLocations(page: Int): ResponseData<LocationListQuery.Locations> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationsWithFilter(page: Int, filter: FilterLocation): ResponseData<LocationListWithFilterQuery.Locations> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocation(id: String): ResponseData<LocationQuery.Location> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocation(ls: List<String>): ResponseData<List<LocationsWithIdsQuery.LocationsById?>> {
        TODO("Not yet implemented")
    }

}