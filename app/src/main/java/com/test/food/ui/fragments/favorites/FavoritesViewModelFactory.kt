package com.test.food.ui.fragments.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.food.ui.main.MainViewModel

class FavoritesViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel() as T
    }
}