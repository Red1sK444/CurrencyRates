package com.r3d1r4ph.currencyrates.ui.currencylist.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.r3d1r4ph.currencyrates.R

class CurrencyItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == 0) return

        val margin8 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)

        outRect.apply {
            top = margin8
            if (position == state.itemCount - 1) {
                bottom = margin8
            }
        }
    }
}