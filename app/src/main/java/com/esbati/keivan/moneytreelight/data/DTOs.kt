package com.esbati.keivan.moneytreelight.data

data class AccountsResponse(
    val accounts: List<Account>
)

data class Account(
    val id: Long,
    val name: String,
    val institution: String,
    val currency: String,
    val current_balance: Double,
    val current_balance_in_base: Double
)

data class TransactionsResponse(
    val transactions: List<Transaction>
)

data class Transaction(
    val account_id: Long,
    val amount: Double,
    val category_id: Long,
    val date: String,
    val description: String,
    val id: Long
)
