package uz.graphql.common_utills.other.extention

import android.graphics.Color


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun Int.toDarkenColor(): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    hsv[2] *= 0.8f
    return Color.HSVToColor(hsv)
}