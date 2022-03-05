package com.esbati.keivan.moneytreelight

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.nio.charset.Charset

class FakeRepository(
    private val context: Context,
    private val io: CoroutineDispatcher
) {
    suspend fun fetchAccounts(): List<Account> = withContext(io) {
        val json = context.loadJSONFromAsset("accounts.json")
        val response = Gson().fromJson(json, AccountsResponse::class.java)
        response.accounts
    }

    suspend fun fetchTransactions(id: Long): List<Transaction> = withContext(io) {
        val json = context.loadJSONFromAsset(
            when (id) {
                1L -> "transactions_1.json"
                2L -> "transactions_2.json"
                3L -> "transactions_3.json"
                else -> "transactions_3.json"
            }
        )
        val response = Gson().fromJson(json, TransactionsResponse::class.java)
        response.transactions
    }
}

data class AccountsResponse(
    val accounts: List<Account>
)

data class TransactionsResponse(
    val transactions: List<Transaction>
)

data class Account(
    val id: Long,
    val name: String,
    val institution: String,
    val currency: String,
    val current_balance: Double,
    val current_balance_in_base: Double
)

data class Transaction(
    val account_id: Long,
    val amount: Double,
    val category_id: Long,
    val date: String,
    val description: String,
    val id: Long
)

private fun Context.loadJSONFromAsset(name: String): String {
    val input = this.assets.open(name)
    val size: Int = input.available()
    val buffer = ByteArray(size)
    input.read(buffer)
    input.close()
    return String(buffer, Charset.forName("UTF-8"))
}