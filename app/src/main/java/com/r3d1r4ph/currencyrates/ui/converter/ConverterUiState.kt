package com.r3d1r4ph.currencyrates.ui.converter

import com.r3d1r4ph.currencyrates.domain.Currency

data class ConverterUiState(
    val currency: Currency,
    val converted: Float = 0f
)
