package com.test.food.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.test.food.BR
import com.test.food.R
import com.test.food.databinding.FragmentHomeBinding
import com.test.food.databinding.LayoutBlockBinding
import com.test.food.ui.base.BaseFragment
import com.test.food.ui.base.recyclerview.BaseAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()

    override val layoutId: Int
        get() = R.layout.fragment_home
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this, factory)[HomeViewModel::class.java]


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mHasCategories.observe(viewLifecycleOwner) {
            if (it) {
                addBlock("Categories", viewModel.mCategoriesAdapter, 1)
            }
        }
        viewModel.mHasRestaurant.observe(viewLifecycleOwner) {
            if (it) {
                addBlock("Featured Restaurant", viewModel.mRestaurantAdapter,2)
            }
        }
        viewModel.getData()
    }

    private fun addBlock(title: String, adapter: BaseAdapter<*>, position : Int) {
        mViewDataBinding?.llContainer?.also {
            val blockViewBinding = DataBindingUtil.inflate<LayoutBlockBinding>(
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                R.layout.layout_block,
                null,
                false
            )
            it.addView(blockViewBinding.root, position)
            blockViewBinding.title = title
            blockViewBinding.executePendingBindings()
            blockViewBinding.rvItemsBlock.adapter = adapter
        }
    }
}