package com.r3d1r4ph.currencyrates.domain

data class General(
    val relevance: Relevance,
    val currencyList: List<Currency>
)
