package com.brainer.mvvmcoroutines.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brainer.mvvmcoroutines.apiutils.API
import com.brainer.mvvmcoroutines.utils.ErrorTypes
import com.brainer.mvvmcoroutines.NetworkLayer
import com.brainer.mvvmcoroutines.model.User
import com.brainer.mvvmcoroutines.utils.NetworkConnection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(
    mContext: Activity
) : ViewModel() {

    var errorMessage = MutableLiveData<Any>()
    var isLoading = MutableLiveData<Boolean>()
    private val networkUtils = NetworkConnection(mContext)

    // Call login API
    @OptIn(DelicateCoroutinesApi::class)
    fun doLogin(): MutableLiveData<User>? {

        val mUserModel = MutableLiveData<User>()
        if (!networkUtils.isConnected()) {
            errorMessage.value = ErrorTypes.ERROR_CONNECTION.ordinal
            return null
        }

        isLoading.value = true

        GlobalScope.launch(Dispatchers.IO) {
            NetworkLayer().getClient()!!.create(API::class.java).userApiCall().enqueue(object : Callback<User?> {
                override fun onFailure(call: Call<User?>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = ErrorTypes.ERROR_SOMETHING_WRONG.ordinal
                }

                override fun onResponse(
                    call: Call<User?>,
                    response: Response<User?>
                ) {
                    if (response.isSuccessful) {
                        mUserModel.value = response.body()
                    }
                    isLoading.value = false
                }
            })
        }
        return mUserModel
    }
}