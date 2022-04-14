package com.test.food.ui.fragments.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.food.ui.main.MainViewModel

class BookViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel() as T
    }
}