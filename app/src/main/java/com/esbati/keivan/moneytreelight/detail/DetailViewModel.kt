package com.esbati.keivan.moneytreelight.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.esbati.keivan.moneytreelight.data.Repository
import com.esbati.keivan.moneytreelight.data.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
    scope: CoroutineScope,
    private val repository: Repository,
    private val id: Long
) : ViewModel() {

    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    // TODO Fetch data on observation not initialization
    init {
        scope.launch {
            // TODO show loading
            try {
                _transactions.postValue(repository.fetchTransactions(id))
            } catch (e: Exception) {
                // TODO show error
            }
        }
    }

    val transactions: LiveData<List<DetailRow>> =
        Transformations.map(_transactions) { transactions ->
            transactions.sortedByDescending { transaction -> transaction.date }
                .groupBy { it.date.substring(0, 7) }
                .map { (date, transactions) ->
                    mutableListOf<DetailRow>().apply {
                        add(
                            MonthHeaderRow(
                                year = date.substring(0, 4),
                                month = date.substring(5, 7),
                                balanceIn = transactions.map { it.amount }.filter { it >= 0 }
                                    .sumOf { it }.toString(),
                                balanceOut = transactions.map { it.amount }.filter { it < 0 }
                                    .sumOf { it }.toString(),
                            )
                        )
                        addAll(
                            transactions.map {
                                TransactionRow(
                                    id = it.id,
                                    date = it.date.substring(8, 10),
                                    detail = it.description,
                                    amount = it.amount.toString()
                                )
                            }
                        )
                    }
                }.flatten()
        }
}

sealed class DetailRow

data class MonthHeaderRow(
    val year: String,
    val month: String,
    val balanceIn: String,
    val balanceOut: String
) : DetailRow()

data class TransactionRow(
    val id: Long,
    val date: String,
    val detail: String,
    val amount: String
) : DetailRow()
