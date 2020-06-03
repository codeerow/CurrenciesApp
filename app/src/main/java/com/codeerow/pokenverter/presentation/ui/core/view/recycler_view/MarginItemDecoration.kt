package com.codeerow.pokenverter.presentation.ui.core.view.recycler_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


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
                position == 0 -> startTopMargin
                parent.adapter?.itemCount == position + 1 -> endTopMargin
                else -> verticalMargin
            }

            bottom = when {
                position == 0 -> startBottomMargin
                parent.adapter?.itemCount == position + 1 -> endBottomMargin
                else -> verticalMargin
            }

            left = when {
                position == 0 -> startLeftMargin
                parent.adapter?.itemCount == position + 1 -> endLeftMargin
                else -> horizontalMargin
            }

            right = when {
                position == 0 -> startRightMargin
                parent.adapter?.itemCount == position + 1 -> endRightMargin
                else -> horizontalMargin
            }
        }
    }
}