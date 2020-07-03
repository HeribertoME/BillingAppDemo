package com.hmelizarraraz.subscriptiondemo.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmelizarraraz.subscriptiondemo.R
import com.hmelizarraraz.subscriptiondemo.model.Country
import com.hmelizarraraz.subscriptiondemo.util.getProgressDrawable
import com.hmelizarraraz.subscriptiondemo.util.loadImage
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var country: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.hasExtra(PARAM_COUNTRY) && intent.getParcelableExtra<Country>(PARAM_COUNTRY) != null) {
            country = intent.getParcelableExtra(PARAM_COUNTRY)
        } else {
            finish()
        }

        populate()
    }

    private fun populate() {
        ivCountryFlag.loadImage(country.flag, getProgressDrawable(this))
        tvCountryName.text = country.countryName
        tvCountryCapital.text = "Capital: ${country.capital}"
        tvCountryArea.text = "Area: ${country.area}"
        tvCountryPopulation.text = "Population: ${country.population}"
        tvCountryRegion.text = "Region: ${country.region}"
    }

    companion object {
        val PARAM_COUNTRY = "country"

        fun getIntent(context: Context, country: Country?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PARAM_COUNTRY, country)
            return intent
        }
    }
}
