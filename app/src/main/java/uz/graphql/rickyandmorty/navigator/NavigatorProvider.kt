package uz.graphql.rickyandmorty.navigator

import uz.graphql.common_utills.activity.Activities
import uz.graphql.common_utills.navigator.Navigator
import uz.graphql.ricky_and_morty_presenter.GoToRickyAndMortyActivity


/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 11:47 AM for Rick And Morty GraphQL.
 */
class NavigatorProvider : Navigator.Provider {

    override fun getActivities(activities: Activities): Navigator {
        return when (activities) {
            Activities.RickyAndMortyActivity -> {
                GoToRickyAndMortyActivity
            }
        }
    }

}


//class NavigatorProvider : Navigator.Provider {
//    override fun getActivities(activities: Activities): Navigator {
//        return when (activities) {
//            Activities.RickyAndMortyActivity -> {
//                GoToRickyAndMortyActivity
//            }
//        }
//    }
//
//}