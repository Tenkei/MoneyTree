package com.esbati.keivan.moneytreelight

import com.esbati.keivan.moneytreelight.data.Account
import com.esbati.keivan.moneytreelight.data.Transaction
import java.lang.Exception

object TestObjects {
    const val ACCOUNT_ID = 1L

    val ACCOUNT = Account(
        id = ACCOUNT_ID,
        name = "account_name",
        institution = "account_institution",
        currency = "account_currency",
        current_balance = 22.5,
        current_balance_in_base = 2306.0
    )

    val TRANSACTION = Transaction(
        account_id = ACCOUNT_ID,
        amount = -442.0,
        category_id = 112,
        date = "2017-05-26T00:00:00+09:00",
        description = "transaction_description",
        id = 21
    )

    val EXCEPTION = Exception("This is a test exception!")
}