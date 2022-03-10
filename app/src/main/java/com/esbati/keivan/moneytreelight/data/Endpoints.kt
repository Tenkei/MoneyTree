package com.esbati.keivan.moneytreelight.data

import android.content.Context
import com.google.gson.Gson
import retrofit2.http.GET
import retrofit2.http.Path
import java.nio.charset.Charset

interface Endpoints {
    @GET("/moneytree/api/v1/accounts")
    suspend fun getAccounts(): AccountsResponse

    @GET("/moneytree/api/v1/accounts/{account_id}/transactions")
    suspend fun getTransactions(@Path("account_id") accountId: Long): TransactionsResponse
}

class FakeEndpoints(
    private val context: Context
) : Endpoints {

    override suspend fun getAccounts(): AccountsResponse {
        val json = context.loadJSONFromAsset("accounts.json")
        return Gson().fromJson(json, AccountsResponse::class.java)
    }

    override suspend fun getTransactions(accountId: Long): TransactionsResponse {
        val json = context.loadJSONFromAsset(
            when (accountId) {
                1L -> "transactions_1.json"
                2L -> "transactions_2.json"
                3L -> "transactions_3.json"
                else -> "transactions_3.json"
            }
        )
        return Gson().fromJson(json, TransactionsResponse::class.java)
    }
}

private fun Context.loadJSONFromAsset(name: String): String {
    val input = this.assets.open(name)
    val size: Int = input.available()
    val buffer = ByteArray(size)
    input.read(buffer)
    input.close()
    return String(buffer, Charset.forName("UTF-8"))
}
