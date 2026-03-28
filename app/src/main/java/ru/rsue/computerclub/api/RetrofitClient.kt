package ru.rsue.computerclub.api

import android.content.Context
import android.content.SharedPreferences
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private lateinit var retrofit: Retrofit
    lateinit var api: ComputerClubApi

    private const val PREFS_NAME = "server_prefs"
    private const val KEY_SERVER_IP = "server_ip"
    private const val DEFAULT_IP = "10.0.2.2"
    private const val SERVER_PORT = 8081

    fun init(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var serverIp = prefs.getString(KEY_SERVER_IP, DEFAULT_IP)

        // Если сохранённого IP нет или это эмулятор — оставляем как есть
        if (serverIp.isNullOrEmpty()) {
            serverIp = DEFAULT_IP
        }

        val baseUrl = "http://$serverIp:$SERVER_PORT/computer_club/"

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ComputerClubApi::class.java)

        android.util.Log.d("RetrofitClient", "Using base URL: $baseUrl")
    }

    // Метод для смены IP без перекомпиляции
    fun setServerIp(context: Context, ip: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_SERVER_IP, ip).apply()
        init(context)  // переинициализация
    }

    fun getCurrentIp(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_SERVER_IP, DEFAULT_IP) ?: DEFAULT_IP
    }
}