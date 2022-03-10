package com.esbati.keivan.moneytreelight.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Repository(
    private val io: CoroutineDispatcher
) {
    private val endpoints: Endpoints = Retrofit.Builder()
        .baseUrl("https://622a009abe12fc4538af08f7.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()

    suspend fun fetchAccounts(): List<Account> = withContext(io) {
        try {
            endpoints.getAccounts().accounts
        } catch (e: Exception) {
            Log.d("Repository", e.message.toString())
            listOf()
        }
    }

    suspend fun fetchTransactions(id: Long): List<Transaction> = withContext(io) {
        try {
            endpoints.getTransactions(id).transactions
        } catch (e: Exception) {
            Log.d("Repository", e.message.toString())
            listOf()
        }
    }
}

