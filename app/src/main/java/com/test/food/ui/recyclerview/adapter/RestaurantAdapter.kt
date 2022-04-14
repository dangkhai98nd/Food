package com.test.food.ui.recyclerview.adapter

import androidx.databinding.ViewDataBinding
import com.test.food.R
import com.test.food.data.models.RestaurantModel
import com.test.food.databinding.ItemRestaurantBinding
import com.test.food.ui.base.recyclerview.BaseAdapter

class RestaurantAdapter : BaseAdapter<RestaurantModel>() {
    override fun onBindViewHolder(binding: ViewDataBinding, data: RestaurantModel, position: Int) {
        (binding as? ItemRestaurantBinding)?.also {
            it.restaurant = data
            it.executePendingBindings()
        }
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_restaurant
}