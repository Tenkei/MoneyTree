package com.esbati.keivan.moneytreelight.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.esbati.keivan.moneytreelight.FakeRepository
import com.esbati.keivan.moneytreelight.Transaction
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
class DetailViewModelTest(
    private val input: List<Transaction>,
    private val output: List<DetailRow>
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: sorted({0})={1}")
        fun data(): Collection<Array<List<Any>>> = listOf(
            arrayOf(listOf(), listOf()),
            arrayOf(TEST_TRANSACTIONS_SINGLE, TEST_ROWS_SINGLE),
            arrayOf(TEST_TRANSACTIONS_MULTIPLE_SAME_MONTH, TEST_ROWS_MULTIPLE_SAME_MONTH),
            arrayOf(TEST_TRANSACTIONS_MULTIPLE, TEST_ROWS_MULTIPLE),
            arrayOf(TEST_TRANSACTIONS_MULTIPLE_UNSORTED, TEST_ROWS_MULTIPLE_SORTED),
        )
    }

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined
    private val scope = TestScope()
    private val repository: FakeRepository = mock()
    private val observer: Observer<List<DetailRow>> = mock()

    private val viewModel = DetailViewModel(scope, repository, TEST_ID)

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
            onBlocking { it.fetchTransactions(TEST_ID) } doReturn input
        }

        viewModel.transactions.observeForever(observer)
        scope.advanceUntilIdle()

        verify(observer).onChanged(output)
    }
}

private const val TEST_ID = 1L
private val TEST_TRANSACTION = Transaction(
    account_id = 2,
    amount = -442.0,
    category_id = 112,
    date = "2017-05-26T00:00:00+09:00",
    description = "transaction_description",
    id = 21
)

private val TEST_TRANSACTIONS_SINGLE = listOf(TEST_TRANSACTION)
private val TEST_ROWS_SINGLE = listOf(
    MonthHeaderRow("2017", "05", "0.0", "-442.0"),
    TransactionRow(21, "26", "transaction_description", "-442.0")
)

private val TEST_TRANSACTIONS_MULTIPLE_SAME_MONTH = listOf(
    TEST_TRANSACTION.copy(id = 11, amount = 150.0),
    TEST_TRANSACTION.copy(id = 12, amount = 100.0),
    TEST_TRANSACTION.copy(id = 13, amount = -100.0),
)
private val TEST_ROWS_MULTIPLE_SAME_MONTH = listOf(
    MonthHeaderRow("2017", "05", "250.0", "-100.0"),
    TransactionRow(11, "26", "transaction_description", "150.0"),
    TransactionRow(12, "26", "transaction_description", "100.0"),
    TransactionRow(13, "26", "transaction_description", "-100.0"),
)

private val TEST_TRANSACTIONS_MULTIPLE = listOf(
    TEST_TRANSACTION.copy(id = 11, date = "2017-05-26T00:00:00+09:00", amount = 150.0),
    TEST_TRANSACTION.copy(id = 12, date = "2017-05-26T00:00:00+09:00", amount = 100.0),
    TEST_TRANSACTION.copy(id = 13, date = "2017-04-26T00:00:00+09:00", amount = -100.0),
)
private val TEST_ROWS_MULTIPLE = listOf(
    MonthHeaderRow("2017", "05", "250.0", "0.0"),
    TransactionRow(11, "26", "transaction_description", "150.0"),
    TransactionRow(12, "26", "transaction_description", "100.0"),
    MonthHeaderRow("2017", "04", "0.0", "-100.0"),
    TransactionRow(13, "26", "transaction_description", "-100.0"),
)

private val TEST_TRANSACTIONS_MULTIPLE_UNSORTED = listOf(
    TEST_TRANSACTION.copy(id = 11, date = "2017-05-26T00:00:00+09:00", amount = 150.0),
    TEST_TRANSACTION.copy(id = 12, date = "2017-04-26T00:00:00+09:00", amount = -100.0),
    TEST_TRANSACTION.copy(id = 13, date = "2017-05-26T00:00:00+09:00", amount = 100.0),
)
private val TEST_ROWS_MULTIPLE_SORTED = listOf(
    MonthHeaderRow("2017", "05", "250.0", "0.0"),
    TransactionRow(11, "26", "transaction_description", "150.0"),
    TransactionRow(13, "26", "transaction_description", "100.0"),
    MonthHeaderRow("2017", "04", "0.0", "-100.0"),
    TransactionRow(12, "26", "transaction_description", "-100.0"),
)
