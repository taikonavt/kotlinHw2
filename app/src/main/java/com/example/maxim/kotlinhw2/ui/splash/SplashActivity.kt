package com.example.maxim.kotlinhw2.ui.splash

import android.os.Handler
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.ui.base.BaseActivity
import com.example.maxim.kotlinhw2.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    private val START_DELAY = 1000L

    override val model : SplashViewModel by viewModel()

    override val layoutRes: Int
        get() = R.layout.activity_splash

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}