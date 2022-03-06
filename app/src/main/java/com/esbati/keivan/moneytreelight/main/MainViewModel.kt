package com.esbati.keivan.moneytreelight.main

import androidx.lifecycle.*
import com.esbati.keivan.moneytreelight.Account
import com.esbati.keivan.moneytreelight.FakeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val scope: CoroutineScope,
    private val repository: FakeRepository,
) : ViewModel() {

    private val _accounts = MutableLiveData<List<Account>>()

    init {
        scope.launch {
            val accounts = repository.fetchAccounts()
            _accounts.postValue(accounts)
        }
    }

    val accounts: LiveData<List<Account>> = Transformations.map(_accounts) {
        it.sortedBy { account -> account.name }
    }
}
