package com.rick_and_morty.common_utills.other.extention

import android.content.Context
import com.rick_and_morty.common_utills.R


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun Int.getMonth(context: Context): String = when (this) {
    0 -> context.getString(R.string.January)
    1 -> context.getString(R.string.February)
    2 -> context.getString(R.string.March)
    3 -> context.getString(R.string.April)
    4 -> context.getString(R.string.May)
    5 -> context.getString(R.string.June)
    6 -> context.getString(R.string.July)
    7 -> context.getString(R.string.August)
    8 -> context.getString(R.string.September)
    9 -> context.getString(R.string.October)
    10 -> context.getString(R.string.November)
    11 -> context.getString(R.string.December)
    else -> context.getString(R.string.WrongMonth)
}

fun Int.getMonthDayCount(cond: Boolean = false): Int = when (this) {
    0 -> 31
    1 -> if (cond) 28 else 29
    2 -> 31
    3 -> 30
    4 -> 31
    5 -> 30
    6 -> 31
    7 -> 31
    8 -> 30
    9 -> 31
    10 -> 30
    11 -> 31
    else -> 0
}

fun Long?.changeNotNull(): Long = this ?: 0L

fun Int?.changeNotNull(): Int = this ?: 0

fun Long.changePhoneNumber(): String {
    val input = this.toString()
    return try {
        val number = "+" + input.substring(0, 3) + " " + input.substring(3, 5) + " " + input.substring(5, 8) + " " + input.substring(8)
        number
    } catch (e: Exception) {
        input
    }
}

fun Int.changePhoneNumber(): String {
    val input = this.toString()
    return try {
        val number = "+" + input.substring(0, 3) + " " + input.substring(3, 5) + " " + input.substring(5, 8) + " " + input.substring(8)
        number
    } catch (e: Exception) {
        input
    }
}