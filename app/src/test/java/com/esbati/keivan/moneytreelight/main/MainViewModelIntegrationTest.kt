package com.esbati.keivan.moneytreelight.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.esbati.keivan.moneytreelight.FakeEndpoints
import com.esbati.keivan.moneytreelight.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MainViewModelIntegrationTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val scope = TestScope()
    private val repository = Repository(Dispatchers.Unconfined, FakeEndpoints())
    private val viewModel = MainViewModel(scope, repository)

    private val observer: Observer<List<MainRow>> = mock()

    @Test
    fun `MainViewModel on successful fetch should show the list of accounts`() {
        scope.advanceUntilIdle()

        viewModel.accounts.observeForever(observer)

        verify(observer).onChanged(
            listOf(
                InstitutionRow("account_institution"),
                AccountRow(
                    id = 1,
                    name = "account_name",
                    balance = "account_currency22.5"
                )
            )
        )
    }
}

