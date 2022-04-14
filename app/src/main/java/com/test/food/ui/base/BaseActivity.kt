package com.test.food.ui.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.test.food.util.CommonUtils

/**
 * process base logic for all activities
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>: BaseAppCompatActivity()  {

    protected var mViewDataBinding: T? = null
        private set
    protected var mViewModel: V? = null
        private set

    /**
     * Overriding for set LayoutId
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int?

    /**
     * Overriding for set binding variable
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * Overriding for set view model
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setupObservers()
    }

    /**
     * Processing binding data
     */
    private fun performDataBinding() {
        mViewDataBinding = layoutId?.let { DataBindingUtil.setContentView(this, it) }
        //
        mViewModel = if (mViewModel == null) viewModel else mViewModel
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.executePendingBindings()
    }

    /**
     * Handling show loading icon, message, snackbar
     */
    private fun setupObservers() {
        mViewModel?.mLoading?.observe(this, Observer {
            if (it != null && it) showLoading()
            else hideLoading()
        })
        mViewModel?.mMessage?.observe(this, Observer {
            it?.let { CommonUtils.showErrorDialog(this, "", it) }
        })
        mViewModel?.mSnackbar?.observe(this, Observer {
            it?.let { CommonUtils.showSnackbar(mViewDataBinding?.root, it) }
        })
    }

    /**
     * Handler action move delay
     */
    fun runDelay(delay: Long, process: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            process()
        }, delay)
    }
}