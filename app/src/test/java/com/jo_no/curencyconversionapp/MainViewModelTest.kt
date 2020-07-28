package com.jo_no.curencyconversionapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jo_no.curencyconversionapp.models.CurrencyRate
import com.jo_no.curencyconversionapp.models.CurrencyResponse
import com.jo_no.curencyconversionapp.ui.main.MainRepo
import com.jo_no.curencyconversionapp.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MainViewModelTest {

    @InjectMocks
    private lateinit var viewModelUnderTest: MainViewModel

    private val mockRepo = Mockito.mock(MainRepo::class.java)

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxImmediateSchedulerRule()

    private val expectedCurrencyList = arrayListOf<CurrencyRate>(
        CurrencyRate("GBP", 1.4),
        CurrencyRate("USD", 1.9),
        CurrencyRate("SEK", 5.2),
        CurrencyRate("HUF", 3.2),
        CurrencyRate("EUR", 1.0)
    )
    private val success: Flowable<CurrencyResponse> =
        Flowable.just(
            CurrencyResponse(
                "EUR",
                mapOf("GBP" to "1.4", "USD" to "1.9", "SEK" to "5.2", "HUF" to "3.2")
            )
        )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModelUnderTest = MainViewModel(mockRepo)
    }

    @After
    fun after() {
    }

    @Test
    fun successfulResponseUpdatesLiveData() {
        whenever(mockRepo.getCurrencyRates()).thenReturn(success)
        viewModelUnderTest.getCurrencyRates()
        assert(expectedCurrencyList == viewModelUnderTest.currencies.value)
    }
}