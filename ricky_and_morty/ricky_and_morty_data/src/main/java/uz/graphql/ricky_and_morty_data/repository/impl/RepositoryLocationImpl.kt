package uz.graphql.ricky_and_morty_data.repository.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import uz.graphql.*
import uz.graphql.common_utills.other.ResponseApi
import uz.graphql.ricky_and_morty_data.model.FilterLocationDTO
import uz.graphql.ricky_and_morty_data.repository.RepositoryLocation
import uz.graphql.type.FilterLocation
import java.io.IOException

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/14/2023 8:52 PM for Rick And Morty GraphQL.
 */
class RepositoryLocationImpl(private var client: ApolloClient) : RepositoryLocation {
    override suspend fun getLocations(page: Int): ResponseApi<LocationListQuery.Locations> {
        return try {
            if (page < 0) {
                ResponseApi.Success(
                    LocationListQuery.Locations(
                        info = null,
                        results = null
                    )
                )
            } else {
                val result = client.query(LocationListQuery(page)).execute()
                val episodes = result.data?.locations
                if (episodes != null)
                    ResponseApi.Success(episodes)
                else ResponseApi.Error("Locations can not find")
            }
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getLocationsWithFilter(page: Int, filterData: FilterLocationDTO): ResponseApi<LocationListWithFilterQuery.Locations> {
        var filter = FilterLocation()
        if (filterData.dimension != null && filterData.dimension.isNotBlank()) {
            filter = filter.copy(dimension = Optional.present(filterData.dimension))
        }
        if (filterData.name != null && filterData.name.isNotBlank()) {
            filter = filter.copy(name = Optional.present(filterData.name))
        }
        if (filterData.type != null && filterData.type.isNotBlank()) {
            filter = filter.copy(type = Optional.present(filterData.type))
        }
        return try {
            if (page < 0) {
                ResponseApi.Success(
                    LocationListWithFilterQuery.Locations(
                        info = null,
                        results = null
                    )
                )
            } else {
                val result = client.query(LocationListWithFilterQuery(filter, page)).execute()
                val episodes = result.data?.locations
                if (episodes != null)
                    ResponseApi.Success(episodes)
                else ResponseApi.Error("Locations can not find")
            }
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getLocation(id: String): ResponseApi<LocationQuery.Location> {
        return try {
            val result = client.query(LocationQuery(id)).execute()
            val episode = result.data?.location
            if (episode != null)
                ResponseApi.Success(episode)
            else ResponseApi.Error("Location is null")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getLocation(ls: List<String>): ResponseApi<List<LocationsWithIdsQuery.LocationsById?>> {
        return try {
            val result = client.query(LocationsWithIdsQuery(ls)).execute()
            val episodes = result.data?.locationsByIds
            if (episodes != null)
                ResponseApi.Success(episodes)
            else ResponseApi.Error("Locations can not find")
        } catch (e: IOException) {
            ResponseApi.Error(e.message ?: "Unknown error")
        } catch (e: Exception) {
            ResponseApi.Error(e.message ?: "Unknown error")
        }
    }

}