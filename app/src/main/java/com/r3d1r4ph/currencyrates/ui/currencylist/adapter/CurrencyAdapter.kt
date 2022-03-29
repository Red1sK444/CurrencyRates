package com.r3d1r4ph.currencyrates.ui.currencylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.currencyrates.R
import com.r3d1r4ph.currencyrates.databinding.ItemRecyclerCurrencyBinding
import com.r3d1r4ph.currencyrates.domain.Currency

class CurrencyAdapter : ListAdapter<Currency, CurrencyAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recycler_currency, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecyclerCurrencyBinding.bind(view)

        fun bind(currency: Currency) = with(binding) {
            itemCurrencyNameTextView.text = currency.name
            itemCurrencyCharCodeTextView.text =
                root.context.getString(R.string.char_code, currency.charCode)
            itemCurrencyNominalTextView.text =
                root.context.getString(R.string.nominal, currency.nominal.toString())
            itemCurrencyValueTextView.text =
                root.context.getString(R.string.current_value, currency.value.toString())
        }
    }
}