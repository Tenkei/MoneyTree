package com.esbati.keivan.moneytreelight.detail

import androidx.lifecycle.*
import com.esbati.keivan.moneytreelight.FakeRepository
import com.esbati.keivan.moneytreelight.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DetailViewModel(
    private val scope: CoroutineScope,
    private val repository: FakeRepository,
    private val id: Long
) : ViewModel() {

    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    init {
        scope.launch {
            val transactions = repository.fetchTransactions(id)
            _transactions.postValue(transactions)
        }
    }

    val transactions: LiveData<List<Transaction>> = Transformations.map(_transactions) {
        it.sortedByDescending { transaction -> transaction.date }
    }
}
