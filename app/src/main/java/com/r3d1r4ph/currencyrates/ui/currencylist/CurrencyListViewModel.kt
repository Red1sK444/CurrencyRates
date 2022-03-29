package com.r3d1r4ph.currencyrates.ui.currencylist

import androidx.lifecycle.*
import com.r3d1r4ph.currencyrates.R
import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepository
import com.r3d1r4ph.currencyrates.domain.General
import com.r3d1r4ph.currencyrates.utils.exceptions.EmptyResponseException
import com.r3d1r4ph.currencyrates.utils.exceptions.ExceptionHolder
import com.r3d1r4ph.currencyrates.utils.exceptions.NoConnectivityException
import com.r3d1r4ph.currencyrates.utils.exceptions.StatusCodeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val general: LiveData<General>
        get() = currencyRepository.getGeneralInfo()
            .onEach { _loading.value = false }
            .asLiveData()

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _exception = MutableLiveData<ExceptionHolder>()
    val exception: LiveData<ExceptionHolder>
        get() = _exception

    init {
        viewModelScope.launch {
            while (true) {
                loadGeneralData()
                delay(100000)
            }
        }
    }

    fun loadGeneralData() {
        viewModelScope.launch {
            _loading.value = true
            val response = currencyRepository.loadCurrencyList()

            if (response.isFailure) {
                handleException(response.exceptionOrNull())
            }
            _loading.value = false
        }
    }

    private fun handleException(exception: Throwable?) {
        when (exception) {
            is NoConnectivityException -> {
                _exception.value = ExceptionHolder.Resource(
                    R.string.no_connectivity_exception
                )
            }
            is EmptyResponseException -> {
                _exception.value = ExceptionHolder.Resource(
                    R.string.empty_response_exception
                )
            }
            is StatusCodeException -> {
                _exception.value = when (val message = exception.message) {
                    null -> ExceptionHolder.Resource(R.string.unknown_exception)
                    else -> ExceptionHolder.Server(message)
                }
            }
            else -> {
                _exception.value = ExceptionHolder.Resource(
                    R.string.unknown_exception
                )
            }
        }
    }
}