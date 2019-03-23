package com.example.maxim.kotlinhw2.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class LogoutDialog : DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
                .setTitle(R.string.logout_dialog_title)
                .setMessage(R.string.logout_dialog_message)
                .setPositiveButton(R.string.ok_btn_title) {
                    _, _ -> (activity as LogoutListener).onLogout()}
                .setNegativeButton(R.string.logout_dealog_cancel){
                    _, _ -> dismiss()}
                .create()
    }

    interface LogoutListener{
        fun onLogout()
    }
}