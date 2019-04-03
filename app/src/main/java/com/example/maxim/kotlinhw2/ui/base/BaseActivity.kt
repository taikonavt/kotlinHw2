package com.example.maxim.kotlinhw2.ui.base

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.errors.NoAuthException
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity <T> : AppCompatActivity(), CoroutineScope{

    private val RC_SIGN_IN = 458

    private lateinit var dataJob: Job
    private lateinit var errorJob: Job
    abstract val model: BaseViewModel<T>
    abstract val layoutRes: Int

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            model.getViewState().consumeEach {
                renderData(it)
            }
        }
        errorJob = launch {
            model.getErrorChannel().consumeEach {
                renderError(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        when(error) {
            is NoAuthException -> startLoginActivity()
            else -> error.message?.let { showError(it) }
        }
    }

    private fun startLoginActivity() {
        val providers = listOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.android_robot)
                        .setTheme(R.style.LoginStyle)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK){
            finish()
        }
    }

    private fun showError(error: String){
        Snackbar.make(mainRecycler, error, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.ok_btn_title) {
                dismiss()
            }
            show()
        }
    }
}