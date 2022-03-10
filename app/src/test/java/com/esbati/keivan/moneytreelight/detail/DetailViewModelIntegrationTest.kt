package com.esbati.keivan.moneytreelight.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.esbati.keivan.moneytreelight.FakeEndpoints
import com.esbati.keivan.moneytreelight.TestObjects
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
class DetailViewModelIntegrationTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val scope = TestScope()
    private val repository = Repository(Dispatchers.Unconfined, FakeEndpoints())
    private val viewModel = DetailViewModel(scope, repository, TestObjects.ACCOUNT_ID)

    private val observer: Observer<List<DetailRow>> = mock()

    @Test
    fun `MainViewModel on successful fetch should show the list of accounts`() {
        scope.advanceUntilIdle()

        viewModel.transactions.observeForever(observer)

        verify(observer).onChanged(
            listOf(
                MonthHeaderRow("2017", "05", "0.0", "-442.0"),
                TransactionRow(21, "26", "transaction_description", "-442.0")
            )
        )
    }
}

