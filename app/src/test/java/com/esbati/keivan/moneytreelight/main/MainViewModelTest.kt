package com.esbati.keivan.moneytreelight.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.esbati.keivan.moneytreelight.Account
import com.esbati.keivan.moneytreelight.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

@RunWith(Parameterized::class)
@ExperimentalCoroutinesApi
class MainViewModelTest(private val input: List<Account>, private val output: List<Account>) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: sorted({0})={1}")
        fun data(): Collection<Array<List<Any>>> = listOf(
            arrayOf(listOf(), listOf()),
            arrayOf(listOf(TEST_ACCOUNT), listOf(TEST_ACCOUNT)),
            arrayOf(TEST_ACCOUNTS_UNSORTED, TEST_ACCOUNTS_SORTED)
        )
    }

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined
    private val scope = TestScope()
    private val repository: FakeRepository = mock()
    private val observer: Observer<List<Account>> = mock()

    private val viewModel = MainViewModel(scope, repository)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `accounts should be sorted by names`() {
        repository.stub {
            onBlocking { it.fetchAccounts() } doReturn input
        }

        viewModel.accounts.observeForever(observer)
        scope.advanceUntilIdle()

        verify(observer).onChanged(output)
    }
}

private val TEST_ACCOUNT = Account(
        id = 1,
        name = "account_name",
        institution = "account_bank",
        currency = "account_currency",
        current_balance = 22.5,
        current_balance_in_base = 2306.0
)

private val TEST_ACCOUNTS_UNSORTED = listOf(
    TEST_ACCOUNT.copy(name = "B"),
    TEST_ACCOUNT.copy(name = "A"),
)

private val TEST_ACCOUNTS_SORTED = listOf(
    TEST_ACCOUNT.copy(name = "A"),
    TEST_ACCOUNT.copy(name = "B"),
)

