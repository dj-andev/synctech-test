package com.example.synchronoustechnologytest.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity: AppCompatActivity(), BaseActivityContract {
    protected lateinit var viewDataBinding: ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, getLayout())
        initiateViews()
        attachViewModelsWithViews()
        attachObserver()
        updateTitle(getPageTitle())
        checkRunTimePermission()
    }

    private fun updateTitle(title: Int){
        if (title != 0){
            supportActionBar?.title = resources.getString(title)
        }
    }

    override fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message,Snackbar.LENGTH_LONG).show()
    }

    abstract fun checkRunTimePermission()
}