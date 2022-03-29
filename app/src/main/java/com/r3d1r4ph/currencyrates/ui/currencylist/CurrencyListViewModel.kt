package com.r3d1r4ph.currencyrates.ui.currencylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepository
import com.r3d1r4ph.currencyrates.domain.General
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

    fun getGeneralData() {
        viewModelScope.launch {
            val response = currencyRepository.getCurrencyList()
            if (response != null) {
                _general.value = response
            }
        }
    }
}