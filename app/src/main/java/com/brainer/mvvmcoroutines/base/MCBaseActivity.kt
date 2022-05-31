package com.brainer.mvvmcoroutines.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brainer.mvvmcoroutines.utils.CustomProgressDialog
import com.brainer.mvvmcoroutines.utils.ErrorTypes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

abstract class MCBaseActivity : AppCompatActivity() {

    private var mProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog =
            CustomProgressDialog(this)
    }

    abstract fun initComponent()

    fun handleErrorMessage(errorMessage: Any) {

        if (errorMessage is String) {
            showError(errorMessage)

        } else {

            when (errorMessage) {
                ErrorTypes.ERROR_SOMETHING_WRONG.ordinal -> showError("something wrong")
                ErrorTypes.ERROR_CONNECTION.ordinal -> showError("connection error")
            }

        }
    }

    open fun showLoader() {
        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.show()
        }
    }

    open fun hideLoader() {
        if (mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    private fun showError(msg: String?) {
        if (msg == null) {
            return
        }
        Snackbar.make(findViewById(android.R.id.content), msg, BaseTransientBottomBar.LENGTH_SHORT)
            .show()
    }
}