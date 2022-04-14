package com.test.food.ui.fragments.search

import androidx.lifecycle.ViewModelProvider
import com.test.food.R
import com.test.food.BR
import com.test.food.databinding.FragmentSearchBinding
import com.test.food.ui.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() , KodeinAware {

    override val kodein by kodein()
    private val factory: SearchViewModelFactory by instance()

    override val layoutId: Int
        get() = R.layout.fragment_search
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: SearchViewModel
        get() = ViewModelProvider(this, factory)[SearchViewModel::class.java]


}