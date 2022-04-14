package com.test.food.ui.base.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter <T> : RecyclerView.Adapter<BaseItemViewHolder>() {

    abstract fun onBindViewHolder(binding : ViewDataBinding, data : T, position: Int)

    abstract fun getLayoutId(viewType: Int) : Int

    open fun getLoadMoreLayoutId() : Int? = null

    private val items = mutableListOf<T>()
    private var loadMoreShowing = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        val layoutId = when (viewType) {
            BaseItemType.ITEM_LOADING_MORE.value -> getLoadMoreLayoutId() ?: throw Exception("load more layout id is null")
            else -> getLayoutId(viewType)
        }
        return BaseItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {
        if (loadMoreShowing && position == this.items.size) return
        onBindViewHolder(holder.binding, items[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == items.size) {
            return BaseItemType.ITEM_LOADING_MORE.value
        }
        return BaseItemType.BASE_ITEM.value
    }

    override fun getItemCount(): Int = items.size + if (loadMoreShowing) 1 else 0

    @SuppressLint("NotifyDataSetChanged")
    fun initialData(items : List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addAll(items: List<T>) {
        this.items.addAll(items)
        notifyItemRangeInserted(this.items.size - items.size, items.size)
    }

    fun add(item : T) {
        this.items.add(item)
        notifyItemInserted(this.items.size - 1)
    }

    fun replace(item : T, position: Int) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    fun remove(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        items.clear()
        notifyDataSetChanged()
    }

    fun showLoadMore() {
        loadMoreShowing = true
        notifyItemInserted(this.items.size)
    }

    fun hideLoadMore() {
        loadMoreShowing = false
        notifyItemRemoved(this.items.size)
    }

    enum class BaseItemType(val value: Int) {
        ITEM_LOADING_MORE(0),
        BASE_ITEM(1)
    }
}