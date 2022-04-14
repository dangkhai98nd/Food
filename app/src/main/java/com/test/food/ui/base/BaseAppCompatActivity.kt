package com.test.food.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.test.food.R
import com.test.food.util.custom.CustomLoading

open class BaseAppCompatActivity : AppCompatActivity() {

    protected val mTag: String = this.javaClass.simpleName
    protected var mContext: Context? = null
        private set
    protected var mHandler: Handler? = null
        private set
    private var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mHandler = Handler(Looper.getMainLooper())
    }

    /**
     * Overriding for set a fragment
     */
    open fun setFragment(fragment: Fragment) {}

    /**
     * Overriding for handling to set text for title on toolbar
     * title: value will be display on toolbar
     * isLarge: variable for checking change textSize
     */
    open fun setTitle(title: String, isLarge: Boolean) {}

    /**
     * Overriding for handling visible toolbar or not
     * value: true or false
     */
    open fun isVisibleToolbar(value: Boolean) {}

    /**
     * Get main root view
     */
    open fun getRootView(): View? = null

    /**
     * Get NavController
     */
    open fun getNavController(): NavController? = null


    /**
     * Checking keyboard show or not
     * view: this is rootView
     */
    fun isKeyboardVisible(view: View): Boolean {
        return ViewCompat.getRootWindowInsets(view)
            ?.isVisible(WindowInsetsCompat.Type.ime())
            ?: false
    }

    /**
     * Processing show keyboard
     * view: currentView focus
     */
    fun showKeyBoard(view: View) {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * Processing hide keyboard
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }

    /**
     * Create and show progress
     */
    @SuppressLint("InflateParams")
    open fun showLoading() {
        if (!isFinishing) {
            mDialog ?: let {
                mDialog = AlertDialog.Builder(this).let {
                    val inflater = this.layoutInflater
                    it.setCancelable(false)
                    it.setView(inflater.inflate(R.layout.dialog_loading, null))
                    it.create().apply {
                        window?.requestFeature(Window.FEATURE_NO_TITLE)
                        window?.attributes?.gravity = Gravity.CENTER
                        window?.setBackgroundDrawableResource(android.R.color.transparent)
                    }
                }
                mDialog?.setOnShowListener {
                    mDialog?.window?.decorView?.findViewById<CustomLoading>(R.id.custom_loading)
                        ?.startAnimation()
                }
            }

            if (mDialog?.isShowing != true) {
                mDialog?.show()
            }
        }
    }

    /**
     * hide progress
     */
    open fun hideLoading() {
        if (!isFinishing && mDialog?.isShowing == true) {
            mDialog?.dismiss()
            mDialog?.window?.decorView?.findViewById<CustomLoading>(R.id.custom_loading)
                ?.stopAnimation()
        }
    }

}