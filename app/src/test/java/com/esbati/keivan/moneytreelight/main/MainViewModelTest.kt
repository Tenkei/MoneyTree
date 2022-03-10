package com.esbati.keivan.moneytreelight.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.esbati.keivan.moneytreelight.TestObjects.ACCOUNT
import com.esbati.keivan.moneytreelight.data.Account
import com.esbati.keivan.moneytreelight.data.Repository
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
class MainViewModelTest(private val input: List<Account>, private val output: List<MainRow>) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: Presentation Model ({0})={1}")
        fun data(): Collection<Array<List<Any>>> = listOf(
            arrayOf(listOf(), listOf()),
            arrayOf(TEST_ACCOUNTS_SINGLE, TEST_ROWS_SINGLE),
            arrayOf(TEST_ACCOUNTS_MULTIPLE_SAME_INSTITUTION, TEST_ROWS_MULTIPLE_SAME_INSTITUTION),
            arrayOf(TEST_ACCOUNTS_MULTIPLE, TEST_ROWS_MULTIPLE),
            arrayOf(TEST_ACCOUNTS_UNSORTED_SAME_INSTITUTION, TEST_ACCOUNTS_SORTED_SAME_INSTITUTION),
            arrayOf(TEST_ACCOUNTS_UNSORTED, TEST_ACCOUNTS_SORTED),
        )
    }

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined
    private val scope = TestScope()
    private val repository: Repository = mock()
    private val observer: Observer<List<MainRow>> = mock()

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

private val TEST_ACCOUNTS_SINGLE = listOf(ACCOUNT)
private val TEST_ROWS_SINGLE = listOf(
    InstitutionRow("account_institution"),
    AccountRow(
        id = 1,
        name = "account_name",
        balance = "account_currency22.5"
    )
)

private val TEST_ACCOUNTS_MULTIPLE_SAME_INSTITUTION = listOf(
    ACCOUNT.copy(id = 1, name = "account_first"),
    ACCOUNT.copy(id = 2, name = "account_second")
)
private val TEST_ROWS_MULTIPLE_SAME_INSTITUTION = listOf(
    InstitutionRow("account_institution"),
    AccountRow(
        id = 1,
        name = "account_first",
        balance = "account_currency22.5"
    ),
    AccountRow(
        id = 2,
        name = "account_second",
        balance = "account_currency22.5"
    )
)

private val TEST_ACCOUNTS_MULTIPLE = listOf(
    ACCOUNT.copy(id = 1, institution = "institution_first"),
    ACCOUNT.copy(id = 2, institution = "institution_second")
)
private val TEST_ROWS_MULTIPLE = listOf(
    InstitutionRow("institution_first"),
    AccountRow(
        id = 1,
        name = "account_name",
        balance = "account_currency22.5"
    ),
    InstitutionRow("institution_second"),
    AccountRow(
        id = 2,
        name = "account_name",
        balance = "account_currency22.5"
    )
)

private val TEST_ACCOUNTS_UNSORTED_SAME_INSTITUTION = listOf(
    ACCOUNT.copy(id = 1, name = "account_B"),
    ACCOUNT.copy(id = 2, name = "account_A"),
)
private val TEST_ACCOUNTS_SORTED_SAME_INSTITUTION = listOf(
    InstitutionRow("account_institution"),
    AccountRow(
        id = 2,
        name = "account_A",
        balance = "account_currency22.5"
    ),
    AccountRow(
        id = 1,
        name = "account_B",
        balance = "account_currency22.5"
    ),
)

private val TEST_ACCOUNTS_UNSORTED = listOf(
    ACCOUNT.copy(
        id = 1,
        name = "account_B",
        institution = "account_B_institution"
    ),
    ACCOUNT.copy(
        id = 2,
        name = "account_A",
        institution = "account_A_institution"
    ),
)
private val TEST_ACCOUNTS_SORTED = listOf(
    InstitutionRow("account_A_institution"),
    AccountRow(
        id = 2,
        name = "account_A",
        balance = "account_currency22.5"
    ),
    InstitutionRow("account_B_institution"),
    AccountRow(
        id = 1,
        name = "account_B",
        balance = "account_currency22.5"
    ),
)

