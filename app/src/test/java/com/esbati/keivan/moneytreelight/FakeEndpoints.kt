package com.esbati.keivan.moneytreelight

import com.esbati.keivan.moneytreelight.TestObjects
import com.esbati.keivan.moneytreelight.data.AccountsResponse
import com.esbati.keivan.moneytreelight.data.Endpoints
import com.esbati.keivan.moneytreelight.data.TransactionsResponse

class FakeEndpoints : Endpoints {
    override suspend fun getAccounts() = AccountsResponse(listOf(TestObjects.ACCOUNT))

    override suspend fun getTransactions(accountId: Long) =
        TransactionsResponse(
            listOf(
                TestObjects.TRANSACTION.copy(account_id = accountId)
            )
        )
}