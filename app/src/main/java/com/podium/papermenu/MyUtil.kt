package com.podium.papermenu

import android.content.Context
import android.content.SharedPreferences
import com.podium.papermenu.model.Food

class MyUtil(val context: Context) {
    var pref: SharedPreferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE)

    fun updateOrder(food: Food, qty: Int) {
        pref.edit().putString(food.name, qty.toString()).apply()
    }

    fun getOrder(food: Food): String? {
        return pref.getString(food.name, "")
    }

    fun getAll(): MutableMap<String, *>? {
        return pref.all
    }

    fun removeOrder(food: Food) {
        pref.edit().remove(food.name).apply()
    }
}