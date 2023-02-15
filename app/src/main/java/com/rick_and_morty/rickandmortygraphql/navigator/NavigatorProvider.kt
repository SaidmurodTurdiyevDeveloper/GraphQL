package com.rick_and_morty.rickandmortygraphql.navigator

import com.rick_and_morty.common_utills.activity.Activities
import com.rick_and_morty.common_utills.navigator.Navigator
import com.rick_and_morty.presenter.GoToRickyAndMortyActivity

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