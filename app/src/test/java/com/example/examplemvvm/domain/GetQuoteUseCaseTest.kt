package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository


    lateinit var getQuoteUseCase: GetQuoteUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getQuoteUseCase = GetQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when The Api doesnt Return Anything The Get Value From Database`() = runBlocking {
        //Given
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()
        // When
        getQuoteUseCase()
        //Then
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDb() }
    }


    @Test
    fun `when the api return something then get values from database`() = runBlocking {
        //Given
        val myList = listOf(Quote("Déjame un comentario", "AristiDevs"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList
        //When
        val response = getQuoteUseCase()
        //Then
        coVerify(exactly = 1) { quoteRepository.clearQuote() }
        coVerify(exactly = 1) { quoteRepository.insertQuote(any()) }
        coVerify(exactly = 0) { quoteRepository.getAllQuotesFromDb() }
        assert(response == myList)
    }
}