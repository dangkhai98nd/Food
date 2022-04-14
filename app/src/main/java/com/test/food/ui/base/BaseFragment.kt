package com.test.food.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.test.food.util.CommonUtils
import com.test.food.ui.main.MainActivity

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    private var mActivity: BaseAppCompatActivity? = null
    protected var mViewDataBinding: T? = null
        private set
    protected var mViewModel: V? = null
        private set

    /**
     * Overriding for set layoutId variable
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseAppCompatActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewDataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(bindingVariable, mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        if (mActivity is MainActivity) setupObservers()
    }

    /**
     * Handling show loading icon, message, snackbar for fragment
     */
    private fun setupObservers() {
        mViewModel?.mLoading?.observe(viewLifecycleOwner, Observer {
            if (it != null && it) showLoading()
            else hideLoading()
        })
        mViewModel?.mMessage?.observe(viewLifecycleOwner, Observer {
            it?.let {
                CommonUtils.showErrorDialog(requireActivity(), "", it)
                mViewModel?.mMessage?.value = null
            }
        })
        mViewModel?.mSnackbar?.observe(viewLifecycleOwner, Observer {
            it?.let {
                CommonUtils.showSnackbar(
                    getRootView(), it, true
                )
                mViewModel?.mSnackbar?.value = null
            }
        })
        mViewModel?.mSnackbarAction?.observe(viewLifecycleOwner, Observer {
            it?.let {
                CommonUtils.showSnackbarAction(
                    getRootView(),
                    it.message, it.actionId, it.listener, true
                )
                mViewModel?.mSnackbarAction?.value = null
            }
        })
    }

    /**
     * Create and show progress
     */
    @SuppressLint("InflateParams")
    open fun showLoading() {
        (activity as? BaseAppCompatActivity)?.showLoading()
    }

    /**
     * hide progress
     */
    open fun hideLoading() {
        (activity as? BaseAppCompatActivity)?.hideLoading()
    }

    /**
     * Execute backPressed
     */
    private fun doBackPressed() {
        mActivity?.onBackPressed()
    }

    /**
     * Base on activity
     */
    protected fun setFragment(fragment: Fragment) {
        mActivity?.setFragment(fragment)
    }

    /**
     * Taking rootView
     */
    private fun getRootView(): View? = mActivity?.getRootView()

    /**
     * Base on activity
     */
    protected fun hideKeyboard() {
        mActivity?.hideKeyboard()
    }

    /**
     * Base on activity
     */
    protected fun showKeyboard(view: View) {
        mActivity?.showKeyBoard(view)
    }
}