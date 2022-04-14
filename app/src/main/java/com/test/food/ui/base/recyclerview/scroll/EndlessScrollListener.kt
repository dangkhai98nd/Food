package com.test.food.ui.base.recyclerview.scroll

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil
import kotlin.math.roundToInt

open class EndlessScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val threshold: Int = 2
) :
    RecyclerView.OnScrollListener() {

    private var previousTotal: Int = 0
    private var isLoading: Boolean = true
    private var page: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItem = layoutManager.itemCount
        val lastVisibleItemPosition: Int
        if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val distanceToTotalItem = totalItem - 1 - lastVisibleItemPosition
            if (distanceToTotalItem in 0..threshold) {
                callLoading()
            } else {
                if (previousTotal < totalItem) {
                    isLoading = false
                }
            }
        } else if (layoutManager is GridLayoutManager) {
            val itemSpan = layoutManager.spanCount.toFloat()
            val totalRow = (totalItem / itemSpan).roundToInt()
            lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val lastVisibleRow = (lastVisibleItemPosition / itemSpan).roundToInt()
            val distanceToTotalRow = totalRow - lastVisibleRow
            if (distanceToTotalRow in 0..threshold) {
                callLoading()
            } else {
                if (previousTotal < totalItem) {
                    isLoading = false
                }
            }

        }
        previousTotal = totalItem
    }

    private fun callLoading() {
        if (isLoading.not()) {
            onLoadMore(page++)
            isLoading = true
        }
    }

    open fun onLoadMore(page: Int) {

    }
}