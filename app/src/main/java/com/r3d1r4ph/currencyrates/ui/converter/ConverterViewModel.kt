package com.r3d1r4ph.currencyrates.ui.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<ConverterUiState>()
    val uiState: LiveData<ConverterUiState>
        get() = _uiState

    fun getCurrencyById(id: String) {
        viewModelScope.launch {
            _uiState.value = ConverterUiState(
                currency = currencyRepository.getCurrencyById(id)
            )
        }
    }

    fun convertToCurrency(rub: Float) {
        viewModelScope.launch {
            _uiState.value?.let {
                _uiState.value = _uiState.value?.copy(
                    converted = rub / (it.currency.value / it.currency.nominal)
                )
            }
        }
    }
}