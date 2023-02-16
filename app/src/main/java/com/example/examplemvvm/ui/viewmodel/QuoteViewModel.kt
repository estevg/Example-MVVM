package com.example.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.model.QuoteModel
import com.example.examplemvvm.data.model.QuoteProvider
import com.example.examplemvvm.domain.GetQuoteUseCase
import com.example.examplemvvm.domain.GetRandomQuoteCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private var getQuotesUseCase: GetQuoteUseCase,
    private var getRandomQuotesUseCase: GetRandomQuoteCase
) : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel?>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {

        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuotesUseCase()

            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)

            }
        }
    }

    fun randomQuote() {
        isLoading.postValue(true)

        /* val currentQuote = QuoteProvider.random()
         quoteModel.postValue(currentQuote)*/

        val quote = getRandomQuotesUseCase()
        if (quote != null) {
            quoteModel.postValue(quote)
        }
        isLoading.postValue(false)

    }


}