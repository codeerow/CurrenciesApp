package com.codeerow.currenciesapp.presentation.ui.core.view.recycler_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codeerow.currenciesapp.presentation.ui.core.view.dp


class MarginItemDecoration(
        private val horizontalMargin: Int = 0, private val verticalMargin: Int = 0,
        private val startTopMargin: Int = verticalMargin,
        private val startLeftMargin: Int = horizontalMargin,
        private val startBottomMargin: Int = verticalMargin,
        private val startRightMargin: Int = horizontalMargin,
        private val endTopMargin: Int = verticalMargin,
        private val endLeftMargin: Int = horizontalMargin,
        private val endBottomMargin: Int = verticalMargin,
        private val endRightMargin: Int = horizontalMargin
) :
        RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            top = when {
                position == 0 -> startTopMargin.dp
                parent.adapter?.itemCount == position + 1 -> endTopMargin.dp
                else -> verticalMargin.dp
            }

            bottom = when {
                position == 0 -> startBottomMargin.dp
                parent.adapter?.itemCount == position + 1 -> endBottomMargin.dp
                else -> verticalMargin.dp
            }

            left = when {
                position == 0 -> startLeftMargin.dp
                parent.adapter?.itemCount == position + 1 -> endLeftMargin.dp
                else -> horizontalMargin.dp
            }

            right = when {
                position == 0 -> startRightMargin.dp
                parent.adapter?.itemCount == position + 1 -> endRightMargin.dp
                else -> horizontalMargin.dp
            }
        }
    }
}