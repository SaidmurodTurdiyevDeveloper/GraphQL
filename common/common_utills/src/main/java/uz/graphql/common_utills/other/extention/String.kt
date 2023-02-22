package uz.graphql.common_utills.other.extention

import android.annotation.SuppressLint
import android.os.Build
import java.time.ZonedDateTime

/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun String.addUZS(): String = "$this UZS"
fun String.addCostUZB(): String = "som/$this"
fun String.addUSD(): String = "$this UZD"
fun String.addCostUSD(): String = "$/$this"

fun String?.changeNotEmpty(): String = this ?: ""


fun String.changeLong(): Long {
    var long = 0L
    this.forEach {
        if (it.isDigit())
            long = 10 * long + it.toString().toLong()
    }
    return long
}

fun String.changeInt(): Int {
    var long = 0
    this.forEach {
        if (it.isDigit())
            long = 10 * long + it.toString().toInt()
    }
    return long
}

fun String.changeTextLong(symbol: Char? = null): String {
    var text = ""
    this.forEach {
        if (it.isDigit())
            text += it.toString()
        else if (symbol != null)
            text += symbol
    }
    return text
}

@SuppressLint("SimpleDateFormat")
fun String?.changeMyDate(): DateData {
    val input = this
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val zone = ZonedDateTime.parse(input)
            return DateData(day = zone.dayOfMonth, month = zone.monthValue + 1, year = zone.year)
        } else {
            input.getMyDate()
        }
    } catch (e: Exception) {
        input.getMyDate()
    }
}

data class DateData(var day: Int = 0, var month: Int = 0, var year: Int = 0, var weak: Int = 0)

fun DateData.getText(symbol: Char = '.'): String {
    return (if (day >= 10) day.toString()
    else "0$day") + symbol + (if (month >= 10) month.toString()
    else "0$month") + symbol + year.toString()
}

fun DateData.dayOfYear(): Int {
    return if (year % 4 == 0) 1 + getDayOfYear()
    else getDayOfYear()
}

private fun DateData.getDayOfYear(): Int {
    return when (month) {
        1 -> day
        2 -> day + 31
        3 -> day + 31 + 28
        4 -> day + 31 + 28 + 31
        5 -> day + 31 + 28 + 31 + 30
        6 -> day + 31 + 28 + 31 + 30 + 31
        7 -> day + 31 + 28 + 31 + 30 + 31 + 30
        8 -> day + 31 + 28 + 31 + 30 + 31 + 30 + 31
        9 -> day + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31
        10 -> day + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30
        11 -> day + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31
        12 -> day + 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30
        else -> day
    }
}

private fun String?.getMyDate(): DateData {
    val input = this.changeNotEmpty().lowercase()
    val myDate = DateData()
    when {
        input.contains("jan") -> myDate.month = 1
        input.contains("feb") -> myDate.month = 2
        input.contains("mar") -> myDate.month = 3
        input.contains("apr") -> myDate.month = 4
        input.contains("may") -> myDate.month = 5
        input.contains("jul") -> myDate.month = 6
        input.contains("jun") -> myDate.month = 7
        input.contains("aug") -> myDate.month = 8
        input.contains("sep") -> myDate.month = 9
        input.contains("oct") -> myDate.month = 10
        input.contains("nov") -> myDate.month = 11
        input.contains("dec") -> myDate.month = 12
    }
    var i = 0
    var j = 0
    var value = ""
    val monthIsDigit = myDate.month == 0
    var isFirstYear = false
    var isFirstMonth = false
    var isFirstDay = true
    while (i < input.length) {
        if (input[i].isDigit()) {
            value += input[i]
            j++
        } else {
            if (j == 4) {
                isFirstMonth = false
                isFirstDay = false
                isFirstYear = true
                i = input.length
            } else if (j == 2) {
                if (value.startsWith("0") && value.changeInt() < 10) {
                    isFirstMonth = true
                    isFirstDay = false
                    isFirstYear = false
                }
                i = input.length
            }
        }
        value = ""
        i++
    }
    val emptyLongText = input.changeTextLong()
    if (monthIsDigit) {
        myDate.month = if (isFirstDay) emptyLongText.substring(2, 4).changeInt() else if (isFirstMonth) emptyLongText.substring(0, 2).changeInt() else emptyLongText.substring(4, 6).changeInt()
    }
    if (monthIsDigit)
        myDate.day = if (isFirstDay) emptyLongText.substring(0, 2).changeInt() else if (isFirstMonth) emptyLongText.substring(2, 4).changeInt() else emptyLongText.substring(6, 8).changeInt()
    else
        myDate.day = if (isFirstDay) emptyLongText.substring(0, 2).changeInt() else emptyLongText.substring(4, 6).changeInt()
    if (monthIsDigit)
        myDate.year = if (isFirstYear) emptyLongText.substring(0, 4).changeInt() else emptyLongText.substring(4, 8).changeInt()
    else
        myDate.year = if (isFirstYear) emptyLongText.substring(0, 4).changeInt() else emptyLongText.substring(2, 6).changeInt()
    if (myDate.month > 12) {
        myDate.month += myDate.day
        myDate.day = myDate.month - myDate.day
        myDate.month = myDate.month - myDate.day
    }
    return myDate
}

