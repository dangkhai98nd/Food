package com.test.food.ui.fragments.profile

import androidx.lifecycle.ViewModelProvider
import com.test.food.R
import com.test.food.BR
import com.test.food.databinding.FragmentProfileBinding
import com.test.food.ui.base.BaseFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), KodeinAware {

    override val kodein by kodein()
    private val factory: ProfileViewModelFactory by instance()

    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: ProfileViewModel
        get() = ViewModelProvider(this, factory)[ProfileViewModel::class.java]


}