package com.oskr19.breakingbad.core.domain

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.showInToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toArrayStringFromJson(): ArrayList<String> {
    val value = this
    val listType = object : TypeToken<ArrayList<String>>() {}.type
    return Gson().fromJson(value, listType)
}

fun ArrayList<String>.toJsonString(): String {
    val list = this
    val gson = Gson()
    return gson.toJson(list)
}