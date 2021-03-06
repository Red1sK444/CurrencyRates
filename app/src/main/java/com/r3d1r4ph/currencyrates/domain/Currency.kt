package com.r3d1r4ph.currencyrates.domain

data class Currency(
    val id: String,
    val nominal: Int,
    val charCode: String,
    val name: String,
    val value: Float
)