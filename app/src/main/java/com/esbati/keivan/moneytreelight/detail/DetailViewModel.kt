package com.esbati.keivan.moneytreelight.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esbati.keivan.moneytreelight.FakeRepository
import com.esbati.keivan.moneytreelight.Transaction
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FakeRepository,
    private val id: Long
) : ViewModel() {

    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    init {
        viewModelScope.launch {
            val transactions = repository.fetchTransactions(id)
            _transactions.postValue(transactions)
        }
    }

    val transactions: LiveData<List<Transaction>>
        get() = _transactions
}
