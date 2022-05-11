package com.podium.papermenu

import android.content.Context
import android.content.SharedPreferences
import com.podium.papermenu.model.Food

class MyUtil(val context: Context) {
    var pref: SharedPreferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE)

    fun updateOrder(food: Food, qty: Int) {
        pref.edit().putString(food.name, "${food.name}::${food.price}::${qty}::${food.category}::${food.image}").apply()
    }

    fun getOrder(food: Food): String {
        return pref.getString(food.name, "").toString()
    }

    fun getAll(): MutableMap<String, *>? {
        return pref.all
    }

    fun removeAll() {
        return pref.edit().clear().apply()
    }

    fun removeOrder(food: String) {
        pref.edit().remove(food).apply()
    }

    fun setTable(table: String) {
        pref.edit().putString("table", table).apply()
    }

    fun getTable(): String {
        return pref.getString("table", "Table One").toString()
    }
}