package com.r3d1r4ph.currencyrates.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.currencyrates.R
import com.r3d1r4ph.currencyrates.databinding.DialogFragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterDialogFragment : DialogFragment() {

    companion object {
        private const val CURRENCY_ID_KEY = "Currency ID Key"

        val TAG: String = ConverterDialogFragment::class.java.simpleName
        fun newInstance(currencyId: String) = ConverterDialogFragment().apply {
            val bundle = Bundle()
            bundle.putString(CURRENCY_ID_KEY, currencyId)
            arguments = bundle
        }
    }

    private val viewModel by viewModels<ConverterViewModel>()
    private val viewBinding by viewBinding(DialogFragmentConverterBinding::bind, R.id.rootLayout)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_fragment_converter,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrencyById(arguments?.getString(CURRENCY_ID_KEY).orEmpty())

        initView()
        initObserver()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)

        viewBinding.converterTextInputEditText.addTextChangedListener {
            viewModel.convertToCurrency(
                if (it.toString().isEmpty()) 0f else it.toString().toFloat()
            )
        }
    }


    private fun initObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            with(viewBinding) {
                converterTitleTextView.text = getString(R.string.convert_in, it.currency.name)

                val rubValue = converterTextInputEditText.text.toString().ifEmpty {
                    0f.toString()
                }

                converterMessageTextView.text = getString(
                    R.string.convert_message,
                    rubValue,
                    it.converted.toString(),
                    it.currency.charCode
                )
            }
        }
    }
}