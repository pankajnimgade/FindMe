package com.search.findme.common.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.search.findme.BuildConfig
import com.search.findme.FindMeApplication
import com.search.findme.di.AppComponent

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var TAG: String

    protected lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initializeAppComponent()
    }

    private fun initializeAppComponent() {
        if (this.application is FindMeApplication && (this.application as FindMeApplication).appComponent != null) {
            (this.application as FindMeApplication).let {
                it.appComponent?.let { appComponent ->
                    this.appComponent = appComponent
                    logMessage(TAG, "Dependency injection has been initialized.")
                }
            }
        } else {
            logMessage(TAG, "Dependency injection has gone wrong at this stage.")
        }
    }

    private fun initialize() {
        TAG = this::class.java.simpleName
        logMessage("initialize", "was called")
    }

    /**
     * This is should be used for [AppCompatActivity] level snack-bars
     */
    protected fun snackBar(view: View?, message: String?) {
        view?.let {
            Snackbar.make(it, "$message", Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * This is should be used for [AppCompatActivity] level logging
     */
    protected fun logMessage(componentName: String, message: String) {
        // A friendly check in case forgotten to remove logger, so that it won't show up in production
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "$componentName :-: $message")
        }
    }
}