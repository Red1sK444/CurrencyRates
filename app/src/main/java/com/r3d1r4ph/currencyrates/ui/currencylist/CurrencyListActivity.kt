package com.r3d1r4ph.currencyrates.ui.currencylist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.currencyrates.R
import com.r3d1r4ph.currencyrates.databinding.ActivityCurrencyListBinding
import com.r3d1r4ph.currencyrates.domain.Currency
import com.r3d1r4ph.currencyrates.ui.converter.ConverterDialogFragment
import com.r3d1r4ph.currencyrates.ui.currencylist.adapter.CurrencyAdapter
import com.r3d1r4ph.currencyrates.ui.currencylist.adapter.CurrencyItemDecoration
import com.r3d1r4ph.currencyrates.utils.exceptions.ExceptionHolder
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class CurrencyListActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityCurrencyListBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<CurrencyListViewModel>()

    private val currencyAdapterListener = object : CurrencyAdapter.CurrencyAdapterListener {
        override fun onItemClick(currency: Currency) {
            showDialog(currencyId = currency.id)
        }
    }
    private val currencyAdapter = CurrencyAdapter(currencyAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)

        initObservers()
        initView()
    }

    private fun initObservers() {
        viewModel.general.observe(this) {
            currencyAdapter.submitList(it.currencyList)
            setRelevantDate(it.relevance.timestamp)
        }

        viewModel.loading.observe(this) {
            with(viewBinding) {
                currencyListSpinnerSpinKitView.isVisible = it
                currencyListUpdateButton.isEnabled = !it
            }
        }

        viewModel.exception.observe(this) { holder ->
            viewBinding.currencyListRelevanceTextView.text = getString(R.string.relevant_on, "")

            val message = when (holder) {
                is ExceptionHolder.Resource -> resources.getString(holder.messageId)
                is ExceptionHolder.Server -> holder.message
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRelevantDate(timestamp: String) {
        val offsetDateTime = OffsetDateTime.parse(timestamp)
        val ldt = offsetDateTime
            .atZoneSameInstant(ZoneId.systemDefault())
            .toLocalDateTime()

        val formatter =
            DateTimeFormatter.ofPattern("HH:mm • dd MMMM yyyy", Locale("ru", "RU"))
        viewBinding.currencyListRelevanceTextView.text =
            getString(R.string.relevant_on, ldt.format(formatter))
    }

    private fun initView() = with(viewBinding) {
        currencyListRecycler.apply {
            adapter = currencyAdapter
            layoutManager =
                LinearLayoutManager(this@CurrencyListActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(CurrencyItemDecoration())
        }

        currencyListRelevanceTextView.text =
            getString(R.string.relevant_on, "")

        currencyListUpdateButton.setOnClickListener {
            viewModel.loadGeneralData()
        }
    }

    private fun showDialog(currencyId: String) {
        ConverterDialogFragment
            .newInstance(currencyId)
            .show(
                supportFragmentManager,
                ConverterDialogFragment.TAG
            )
    }
}