package com.test.food.ui.fragments.book

import androidx.lifecycle.ViewModelProvider
import com.test.food.R
import com.test.food.BR
import com.test.food.databinding.FragmentBookBinding
import com.test.food.ui.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class BookFragment : BaseFragment<FragmentBookBinding, BookViewModel>(), KodeinAware {

    override val kodein by kodein()
    private val factory: BookViewModelFactory by instance()

    override val layoutId: Int
        get() = R.layout.fragment_book
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: BookViewModel
        get() = ViewModelProvider(this, factory)[BookViewModel::class.java]


}