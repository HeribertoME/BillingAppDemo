package com.hmelizarraraz.subscriptiondemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmelizarraraz.subscriptiondemo.BuildConfig
import com.hmelizarraraz.subscriptiondemo.R
import com.hmelizarraraz.subscriptiondemo.adapter.CountryClickListener
import com.hmelizarraraz.subscriptiondemo.adapter.CountryListAdapter
import com.hmelizarraraz.subscriptiondemo.model.Country
import com.hmelizarraraz.subscriptiondemo.presenter.CountriesPresenter
import com.hmelizarraraz.subscriptiondemo.util.BillingAgent
import com.hmelizarraraz.subscriptiondemo.util.BillingCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountryClickListener, CountriesPresenter.View, BillingCallback {

    private val countriesList = arrayListOf<Country>()
    private val countriesAdapter = CountryListAdapter(arrayListOf(), this)
    private var billingAgent: BillingAgent? = null
    private var clickedCountry: Country? = null

    private val presenter = CountriesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countriesAdapter
        }

        billingAgent = BillingAgent(this, this)
    }

    override fun onDestroy() {
        billingAgent?.onDestroy()
        billingAgent = null
        super.onDestroy()
    }

    fun onRetry(v: View) {
        presenter.onRetry()

        btnRetry.visibility = View.GONE
        pbProgress.visibility = View.VISIBLE
        rvList.visibility = View.GONE
    }

    override fun onCountryClick(country: Country) {
//        if (BuildConfig.FLAVOR == "free") {
//            // some implementation for free
//            Toast.makeText(this, "You are FREE", Toast.LENGTH_LONG).show()
//            startActivity(DetailActivity.getIntent(this, country))
//        } else {
//            startActivity(DetailActivity.getIntent(this, country))
//        }
        clickedCountry = country
        //billingAgent?.purchaseView()
        billingAgent?.purchaseSubscriptionView()
    }

    override fun setCountries(countries: List<Country>?) {
        countriesList.clear()
        countries?.let {
            countriesList.addAll(it)
        }
        countriesAdapter.updateCountries(countriesList)
        btnRetry.visibility = View.GONE
        pbProgress.visibility = View.GONE
        rvList.visibility = View.VISIBLE

    }

    override fun onError() {
        Toast.makeText(this, "Unable to get countries list, try again", Toast.LENGTH_SHORT).show()
        btnRetry.visibility = View.VISIBLE
        pbProgress.visibility = View.GONE
        rvList.visibility = View.GONE
    }

    override fun onTokenConsumed() {
        startActivity(DetailActivity.getIntent(this@MainActivity, clickedCountry))
    }
}
