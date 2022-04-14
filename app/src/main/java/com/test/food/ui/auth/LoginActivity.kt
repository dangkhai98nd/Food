package com.test.food.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.test.food.R
import com.test.food.BR
import com.test.food.data.preferences.PreferenceProvider
import com.test.food.databinding.ActivityLoginBinding
import com.test.food.ui.base.BaseActivity
import com.test.food.ui.main.MainActivity
import com.test.food.util.OpenTextWatcher
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity<ActivityLoginBinding, AuthViewModel>(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    override val layoutId: Int
        get() = R.layout.activity_login
    override val bindingVariable: Int
        get() = BR.viewmodel
    override val viewModel: AuthViewModel
        get() = ViewModelProvider(this, factory)[AuthViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLoggedInUser().observe(this, { user ->
                if (user != null) {
                    PreferenceProvider(this).saveLogin(true)
                    Intent(this, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    }
                }
            })
        mViewDataBinding?.edtPassword?.addTextChangedListener(object : OpenTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    viewModel.passwordErrorId.postValue(null)
                }
            }
        })
        mViewDataBinding?.edtUsername?.addTextChangedListener(object : OpenTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.isNotEmpty() == true) {
                    viewModel.usernameErrorId.postValue(null)
                }
            }
        })
    }
}
