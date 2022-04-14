package com.test.food.ui.auth

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.test.food.R
import com.test.food.data.db.entities.User
import com.test.food.data.repositories.UserRepository
import com.test.food.ui.base.BaseViewModel
import gmo.androidbase.mvvm.util.ApiException
import gmo.androidbase.mvvm.util.NoInternetException
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    var username: String? = null
    var password: String? = null
    val usernameErrorId = MutableLiveData<Int?>()
    val passwordErrorId = MutableLiveData<Int?>()

    fun getLoggedInUser() = userRepository.getUser()

    fun onLoginButtonClick() {
        if (!checkValidate())
            return
        mLoading.value = true
        ioScope.launch {
            try {
                val authResponse = userRepository.userLogin(username!!, password!!)
                mLoading.postValue(false)
                authResponse.accessToken.let {
                    userRepository.saveUser(User(email = username, password = password))
                    return@launch
                }
            } catch (e: ApiException) {
                mLoading.postValue(false)
                mSnackbar.postValue(e.message)
            } catch (e: NoInternetException) {
                mLoading.postValue(false)
                mSnackbar.postValue(e.message)
            }
        }
    }

    /*Check validate username and password*/
    private fun checkValidate(): Boolean {
        var isMail = false
        var isPass = false
        if (username.isNullOrEmpty()) {
            usernameErrorId.postValue(R.string.username_is_blank)
        } else {
            usernameErrorId.postValue(null)
            isMail = true
        }
        when {
            password.isNullOrEmpty() -> {
                passwordErrorId.postValue(R.string.password_is_blank)
            }
            password?.contains(" ")!! -> {
                passwordErrorId.postValue(R.string.password_not_contain_space)
            }
            else -> {
                passwordErrorId.postValue(null)
                isPass = true
            }
        }

        return isMail && isPass
    }
}