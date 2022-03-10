package com.esbati.keivan.moneytreelight.data

import com.esbati.keivan.moneytreelight.TestObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.fail
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
class RepositoryTest {

    private val dispatcher = Dispatchers.Unconfined
    private val endpoints = mock<Endpoints>()
    private val repository = Repository(dispatcher, endpoints)

    @Test
    fun `fetchAccounts on success should return list of accounts`() = runTest {
        endpoints.stub {
            onBlocking { it.getAccounts() } doReturn AccountsResponse(
                listOf(
                    TestObjects.ACCOUNT
                )
            )
        }

        val results = repository.fetchAccounts()

        results shouldBeEqualTo listOf(TestObjects.ACCOUNT)
    }

    @Test
    fun `fetchAccounts on error should throw an error`() = runTest {
        endpoints.stub { onBlocking { it.getAccounts() } doAnswer { throw TestObjects.EXCEPTION } }

        try {
            repository.fetchAccounts()
            fail("An exception was expected!")
        } catch (e: Exception) {
            e shouldBeEqualTo TestObjects.EXCEPTION
        }
    }

    @Test
    fun `fetchTransactions on success should return list of transactions`() = runTest {
        endpoints.stub {
            onBlocking { it.getTransactions(TestObjects.ACCOUNT_ID) } doReturn TransactionsResponse(
                listOf(
                    TestObjects.TRANSACTION
                )
            )
        }

        val results = repository.fetchTransactions(TestObjects.ACCOUNT_ID)

        results shouldBeEqualTo listOf(TestObjects.TRANSACTION)
    }

    @Test
    fun `fetchTransactions on error should throw an error`() = runTest {
        endpoints.stub { onBlocking { it.getTransactions(TestObjects.ACCOUNT_ID) } doAnswer { throw TestObjects.EXCEPTION } }

        try {
            repository.fetchTransactions(TestObjects.ACCOUNT_ID)
            fail("An exception was expected!")
        } catch (e: Exception) {
            e shouldBeEqualTo TestObjects.EXCEPTION
        }
    }
}
