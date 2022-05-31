package com.brainer.mvvmcoroutines

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brainer.mvvmcoroutines.viewmodels.UserViewModel

class MainViewModelFactory(private val mContext: Activity): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(mContext) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}