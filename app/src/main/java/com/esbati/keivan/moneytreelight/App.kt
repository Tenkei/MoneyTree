package com.esbati.keivan.moneytreelight

import android.app.Application
import com.esbati.keivan.moneytreelight.data.Endpoints
import com.esbati.keivan.moneytreelight.data.Repository
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.init {
            single {
                Retrofit.Builder()
                    .baseUrl("https://622a009abe12fc4538af08f7.mockapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            single { Dispatchers.IO }
            single { Repository(get(), get<Retrofit>().create()) }
        }
    }
}