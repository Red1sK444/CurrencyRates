package com.r3d1r4ph.currencyrates.domain

data class Currency(
    val id: String,
    val numCode: String,
    val charCode: String,
    val name: String,
    val value: Float
)