package com.hmelizarraraz.subscriptiondemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmelizarraraz.subscriptiondemo.R
import com.hmelizarraraz.subscriptiondemo.model.Country
import com.hmelizarraraz.subscriptiondemo.util.getProgressDrawable
import com.hmelizarraraz.subscriptiondemo.util.loadImage
import kotlinx.android.synthetic.main.row_layout.view.*

class CountryListAdapter(var countries: ArrayList<Country>, val listener: CountryClickListener) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: ArrayList<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder.from(parent)

    override fun getItemCount(): Int = countries.size


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.bind(countries[position], listener)

    class CountryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun from(parent: ViewGroup): CountryViewHolder =
                CountryViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.row_layout,
                        parent,
                        false
                    )
                )
        }

        fun bind(country: Country, listener: CountryClickListener) {
            view.tvName.text = country.countryName
            view.tvCapital.text = country.capital
            view.ivPhoto.loadImage(country.flag, getProgressDrawable(view.ivPhoto.context))

            view.llRoot.setOnClickListener { listener.onCountryClick(country) }

        }

    }
}