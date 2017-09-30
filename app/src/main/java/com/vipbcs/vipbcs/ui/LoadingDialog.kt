package com.vipbcs.vipbcs.ui

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by wanghao on 2017/9/27.
 */
object LoadingDialog {
    private var dialog: ProgressDialog? = null

    fun show(context: Context) {
        if (dialog == null) {
            dialog = ProgressDialog(context)
            dialog?.setMessage("Loading ...")
            dialog?.setCancelable(false)
        }
        dialog?.show()
    }

    fun dismiss() {
        if (true == dialog?.isShowing) {
            dialog?.dismiss()
        }
    }
}