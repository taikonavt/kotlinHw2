package com.example.maxim.kotlinhw2.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Handler
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.ui.base.BaseActivity
import com.example.maxim.kotlinhw2.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    private val START_DELAY = 1000L

    override val viewModel : SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int
        get() = R.layout.activity_splash

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({viewModel.requestUser()}, START_DELAY)
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