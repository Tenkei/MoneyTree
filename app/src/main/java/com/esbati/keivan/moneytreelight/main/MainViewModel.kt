package com.esbati.keivan.moneytreelight.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esbati.keivan.moneytreelight.Account
import com.esbati.keivan.moneytreelight.FakeRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: FakeRepository
) : ViewModel() {

    private val _accounts: MutableLiveData<List<Account>> = MutableLiveData()

    init {
        viewModelScope.launch {
            val accounts = repository.fetchAccounts()
            _accounts.postValue(accounts)
        }
    }

    val accounts: LiveData<List<Account>>
        get() = _accounts
}
