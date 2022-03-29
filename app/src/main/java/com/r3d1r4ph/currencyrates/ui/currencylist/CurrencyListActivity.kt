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
import com.r3d1r4ph.currencyrates.utils.exceptions.ExceptionHolder
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class CurrencyListActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityCurrencyListBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<CurrencyListViewModel>()
    private val currencyAdapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)

        initObservers()
        initView()
    }

    private fun initObservers() {
        viewModel.general.observe(this) {
            currencyAdapter.submitList(it.currencyList)

            val offsetDateTime = OffsetDateTime.parse(it.relevance.timestamp)
            val instant = offsetDateTime.toInstant()
            val ldt = offsetDateTime
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()

            val myDate = Date.from(instant)

            val formatter =
                DateTimeFormatter.ofPattern("HH:mm:ss • dd MMMM yyyy", Locale("ru", "RU"))
            val localFormatter = SimpleDateFormat("HH:mm:ss • dd MMMM yyyy", Locale("ru", "RU"))
            //val formattedDate: String = localFormatter.format(offsetDateTime.toLocalDateTime())
            viewBinding.currencyListRelevanceTextView.text =
                getString(R.string.relevant_on, ldt.format(formatter))
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

    private fun initView() = with(viewBinding) {
        currencyListRecycler.apply {
            adapter = currencyAdapter
            layoutManager =
                LinearLayoutManager(this@CurrencyListActivity, LinearLayoutManager.VERTICAL, false)
        }

        currencyListRelevanceTextView.text =
            getString(R.string.relevant_on, "")

        currencyListUpdateButton.setOnClickListener {
            viewModel.loadGeneralData()
        }
    }

    private fun getDateLocaleFromUTC(date: String): String {
        var time = ""
        val utcFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        utcFormatter.timeZone = TimeZone.getTimeZone("UTC")
        var gpsUTCDate: Date? = null
        try {
            gpsUTCDate = utcFormatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val localFormatter = SimpleDateFormat("HH:mm • dd MMMM yyyy", Locale("ru", "RU"))
        localFormatter.timeZone = TimeZone.getDefault()
        gpsUTCDate?.let {
            time = localFormatter.format(it.time)
        }
        return time
    }
}