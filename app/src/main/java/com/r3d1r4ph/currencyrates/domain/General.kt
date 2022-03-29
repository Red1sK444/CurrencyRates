package com.r3d1r4ph.currencyrates.domain

data class General(
    val timestamp: String,
    val currencyList: List<Currency>
)
