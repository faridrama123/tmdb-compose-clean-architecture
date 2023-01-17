package com.example.tmdbapicompose.domain.utils

import android.util.Log
import androidx.navigation.NavHostController


fun NavHostController.superNavigate(route: String) {
    try {
        navigate(route)
    } catch (e: Exception) {
        showLog(e.toString())
    }
}

fun NavHostController.popNavigate(route: String) {
    try {
        popBackStack()
        navigate(route)
    } catch (e: Exception) {
        showLog(e.toString())
    }
}

fun showLog(str: String) = Log.d("LogTag", str)

//fun showToast(context: Context,str: String) = Toast.makeText(context,str,Toast.LENGTH_SHORT).show()