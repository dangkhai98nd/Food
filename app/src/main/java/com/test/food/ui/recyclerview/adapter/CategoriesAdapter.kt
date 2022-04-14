package com.test.food.ui.recyclerview.adapter

import androidx.databinding.ViewDataBinding
import com.test.food.R
import com.test.food.data.models.CategoryModel
import com.test.food.databinding.ItemCategoryBinding
import com.test.food.ui.base.recyclerview.BaseAdapter

class CategoriesAdapter : BaseAdapter<CategoryModel>() {
    override fun onBindViewHolder(binding: ViewDataBinding, data: CategoryModel, position: Int) {
        (binding as? ItemCategoryBinding)?.also {
            it.category = data
            it.executePendingBindings()
        }
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_category
}