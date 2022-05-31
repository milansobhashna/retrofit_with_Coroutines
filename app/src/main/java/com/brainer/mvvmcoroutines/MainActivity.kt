package com.brainer.mvvmcoroutines

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.brainer.mvvmcoroutines.base.MCBaseActivity
import com.brainer.mvvmcoroutines.viewmodels.UserViewModel


class MainActivity : MCBaseActivity() {

    private var mUserViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent()
    }

    override fun initComponent() {
        val factory = MainViewModelFactory(this)
        mUserViewModel = ViewModelProvider(this,factory)[UserViewModel::class.java]
        mUserViewModel!!.errorMessage.observe(this) { handleErrorMessage(it) }
        mUserViewModel!!.isLoading.observe(this) {
            when {
                it -> showLoader()
                else -> hideLoader()
            }
        }
        apiCall()
    }

    private fun apiCall() {
        mUserViewModel!!.doLogin()?.observe(
            this
        ) {
            when {
                it != null -> {
                    Log.e("SUCCESS", it.toString())
                }
            }
        }
    }
}