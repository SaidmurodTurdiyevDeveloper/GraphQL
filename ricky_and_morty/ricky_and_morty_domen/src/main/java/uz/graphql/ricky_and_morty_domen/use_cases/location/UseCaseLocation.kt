package uz.graphql.ricky_and_morty_domen.use_cases.location

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/18/2023 3:01 PM for Rick And Morty GraphQL.
 */
data class UseCaseLocation(
    val getLocation: GetLocation,
    val getLocationsList: GetLocationsList,
    val getLocationsListWithFilter: GetLocationsListWithFilter,
    val getLocationsListWithIds: GetLocationsListWithIds
)
