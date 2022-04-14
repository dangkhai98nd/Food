package com.test.food.ui.fragments.favorites

import androidx.lifecycle.ViewModelProvider
import com.test.food.R
import com.test.food.BR
import com.test.food.databinding.FragmentFavoritesBinding
import com.test.food.ui.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() , KodeinAware {

    override val kodein by kodein()
    private val factory: FavoritesViewModelFactory by instance()

    override val layoutId: Int
        get() = R.layout.fragment_favorites
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: FavoritesViewModel
        get() = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]


}