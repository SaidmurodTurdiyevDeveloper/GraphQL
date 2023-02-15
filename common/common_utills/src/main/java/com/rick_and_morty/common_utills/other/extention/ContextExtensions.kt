package com.rick_and_morty.common_utills.other.extention

import android.content.Context
import android.widget.Toast


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun Context.showToast(text: String) = if (text != "") Toast.makeText(this, text, Toast.LENGTH_SHORT).show() else null
