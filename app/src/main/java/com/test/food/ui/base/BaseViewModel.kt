package com.test.food.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.food.data.network.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel() : ViewModel() {
    val mLoading = MutableLiveData<Boolean>().apply { value = false }
    val mMessage = MutableLiveData<String>()
    val mSnackbar = MutableLiveData<String>()
    val mSnackbarAction = MutableLiveData<SnackbarAction>()

    // coroutines
    private var viewModelJob = Job()
    private val ioContext = viewModelJob + Dispatchers.IO // background context
    private val uiContext = viewModelJob + Dispatchers.Main // ui context
    protected val ioScope = CoroutineScope(ioContext)
    protected val uiScope = CoroutineScope(uiContext)

    /**
     * Init viewModel
     */
    open fun initViewModel() {}

    /**
     * Clearing viewModelJob when this viewModel already destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Handling show message or snackbar
     */
    fun handleError(message: String?, code: String?) {
        if (code == Status.NETWORK_ERROR.value.toString()) {
            mSnackbar.postValue(message!!)
        } else {
            mMessage.postValue(message!!)
        }
    }

    data class SnackbarAction(
        val message: String,
        val actionId: Int,
        val listener: View.OnClickListener
    )
}