package com.payle

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DebtManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("debts", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveDebt(debt: Debt) {
        val debts = getDebts().toMutableList()
        debts.add(debt)
        prefs.edit().putString("debts_list", gson.toJson(debts)).apply()
    }

    fun getDebts(): List<Debt> {
        val json = prefs.getString("debts_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Debt>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
