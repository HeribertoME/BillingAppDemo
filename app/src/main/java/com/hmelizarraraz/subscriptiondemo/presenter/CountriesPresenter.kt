package com.hmelizarraraz.subscriptiondemo.presenter

import com.hmelizarraraz.subscriptiondemo.model.CountriesService
import com.hmelizarraraz.subscriptiondemo.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesPresenter(val view: View) {

    private val service = CountriesService()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        service.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(t: List<Country>) {
                    view.setCountries(t)
                }
                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    view.onError()
                }
            })
    }

    fun onRetry() {
        fetchCountries()
    }

    interface View {
        fun setCountries(countries: List<Country>?)
        fun onError()
    }
}