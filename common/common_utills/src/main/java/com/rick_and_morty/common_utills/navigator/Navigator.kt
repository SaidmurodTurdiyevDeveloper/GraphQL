package com.rick_and_morty.common_utills.navigator

import android.app.Activity
import com.rick_and_morty.common_utills.activity.Activities

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 1/7/2023.
 */
interface Navigator {
    fun navigate(activity:Activity)

    interface Provider{
        fun getActivities(activities: Activities): Navigator
    }
}