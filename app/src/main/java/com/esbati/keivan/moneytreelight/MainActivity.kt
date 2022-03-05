package com.esbati.keivan.moneytreelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = FakeRepository(this, Dispatchers.IO)
        GlobalScope.launch {
            val accounts = repository.fetchAccounts()
            val transactions = repository.fetchTransactions(1)
        }
    }
}