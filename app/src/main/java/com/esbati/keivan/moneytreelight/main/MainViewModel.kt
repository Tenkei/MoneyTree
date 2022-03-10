package com.esbati.keivan.moneytreelight.main

import androidx.lifecycle.*
import com.esbati.keivan.moneytreelight.data.Account
import com.esbati.keivan.moneytreelight.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    scope: CoroutineScope,
    private val repository: Repository,
) : ViewModel() {

    private val _accounts = MutableLiveData<List<Account>>()

    // TODO Fetch data on observation not initialization
    init {
        scope.launch {
            // TODO show loading
            try {
                _accounts.postValue(repository.fetchAccounts())
            } catch (e: Exception) {
                // TODO show error
            }
        }
    }

    val accounts: LiveData<List<MainRow>> = Transformations.map(_accounts) { accounts ->
        accounts.sortedBy { it.name }
            .groupBy { it.institution }
            .map { (institution, accounts) ->
                mutableListOf<MainRow>().apply {
                    add(InstitutionRow(institution))
                    addAll(
                        accounts.map {
                            AccountRow(it.id, it.name, it.currency + it.current_balance)
                        }
                    )
                }
            }.flatten()
    }
}

sealed class MainRow

data class InstitutionRow(
    val name: String
): MainRow()

data class AccountRow(
    val id: Long,
    val name: String,
    val balance: String
): MainRow()
