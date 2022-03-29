package com.r3d1r4ph.currencyrates.ui.currencylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.currencyrates.R
import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepository
import com.r3d1r4ph.currencyrates.domain.General
import com.r3d1r4ph.currencyrates.utils.exceptions.EmptyResponseException
import com.r3d1r4ph.currencyrates.utils.exceptions.ExceptionHolder
import com.r3d1r4ph.currencyrates.utils.exceptions.NoConnectivityException
import com.r3d1r4ph.currencyrates.utils.exceptions.StatusCodeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _general = MutableLiveData<General>()
    val general: LiveData<General>
        get() = _general

    private val _exception = MutableLiveData<ExceptionHolder>()
    val exception: LiveData<ExceptionHolder>
        get() = _exception

    fun getGeneralData() {
        viewModelScope.launch {
            val response = currencyRepository.getCurrencyList()
            if (response.isSuccess) {
                _general.value = response.getOrThrow()
            } else {
                handleException(response.exceptionOrNull())
            }

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