package com.test.food.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import com.test.food.R
import com.test.food.data.models.CategoryModel
import com.test.food.data.models.RestaurantModel
import com.test.food.ui.base.BaseViewModel
import com.test.food.ui.recyclerview.adapter.CategoriesAdapter
import com.test.food.ui.recyclerview.adapter.RestaurantAdapter

class HomeViewModel : BaseViewModel() {
    val mCategoriesAdapter by lazy { CategoriesAdapter() }
    val mRestaurantAdapter by lazy { RestaurantAdapter() }
    val mHasCategories = MutableLiveData<Boolean>(false)
    val mHasRestaurant = MutableLiveData<Boolean>(false)

    fun getData() {
        val categories = listOf(
            CategoryModel(R.drawable.category_1),
            CategoryModel(R.drawable.category_2),
            CategoryModel(R.drawable.category_1),
            CategoryModel(R.drawable.category_2),
            CategoryModel(R.drawable.category_1)
        )

        mCategoriesAdapter.initialData(categories)
        mHasCategories.value = true

        val restaurantList = listOf(
            RestaurantModel(R.drawable.restaurant_1, "Piccola Cucina", "583 Lilac St.", "Massapequa, NY 11758"),
            RestaurantModel(R.drawable.restaurant_2, "Empire strek H", "183 Lilac St.", "Massapequa, NY 11758"),
            RestaurantModel(R.drawable.restaurant_1, "Piccola Cucina", "583 Lilac St.", "Massapequa, NY 11758"),
            RestaurantModel(R.drawable.restaurant_2, "Empire strek H", "183 Lilac St.", "Massapequa, NY 11758")
        )

        mRestaurantAdapter.initialData(restaurantList)
        mHasRestaurant.value = true
    }
}