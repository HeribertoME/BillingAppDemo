package com.hmelizarraraz.subscriptiondemo.adapter

import com.hmelizarraraz.subscriptiondemo.model.Country

interface CountryClickListener {
    fun onCountryClick(country: Country)
}