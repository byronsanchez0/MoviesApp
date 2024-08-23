package com.example.moviesapp.screens.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.moviesapp.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SignUpViewModel (private val context: Context): ViewModel(){
    fun registerUser(username: String, password: String): Boolean {
        val gson = Gson()
        try {
            val listType = object : TypeToken<ArrayList<User>>() {}.type
            val sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
            val usersJson = sharedPreferences?.getString("users", "")
            if (usersJson == "") {
                val users = ArrayList<User>()
                users.add(
                    User(
                        1,
                        username,
                        password,
                    ),
                )
                val json = gson.toJson(users)
                sharedPreferences.edit().putString("users", json).apply()
            } else {
                val users : ArrayList<User> = gson.fromJson(usersJson, listType)
                users.add(
                    User(
                        users.size + 1,
                        username,
                        password,
                    )
                )
                val json = gson.toJson(users)
                sharedPreferences.edit().putString("users", json).apply()
            }
        } catch (e : Exception) {
            return false
        }
        return true
    }
}