package com.example.maxim.kotlinhw2.ui.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.maxim.kotlinhw2.R
import kotlinx.android.synthetic.main.content_main.*

abstract class BaseActivity <T, S : BaseViewState<T>> : AppCompatActivity(){

    abstract val viewModel : BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        viewModel.getViewState().observe(this, Observer <S>{viewState ->
            if (viewState == null) return@Observer
            if (viewState.data != null) renderData(viewState.data)
            if (viewState.error != null) renderError(viewState.error)
        })
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) {
        if (error.message != null) showError(error.message!!)
    }

    protected fun showError(error: String){
        val snackbar = Snackbar.make(mainRecycler, error, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.ok_btn_title) {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}