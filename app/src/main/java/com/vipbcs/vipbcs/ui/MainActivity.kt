package com.vipbcs.vipbcs.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.uuzuche.lib_zxing.activity.CaptureActivity
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.vipbcs.vipbcs.model.ReqCallback
import com.vipbcs.vipbcs.model.TransactionRespResult
import com.vipbcs.vipbcs.utils.EthUtils
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val REQUEST_CODE_SCAN = 888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
    }

    private fun getContentView(): View {
        return verticalLayout {
            //扫描二维码
            val button1 = button()
            button1.text = "扫描二维码"
            button1.onClick { scan() }

//            //连接以太坊查询交易
//            val button2 = button()
//            button2.text = "查询交易"
//            button2.onClick { queryTransaction() }
        }
    }

    private fun scan() {
        ZXingLibrary.initDisplayOpinion(this)
        startActivityForResult(Intent(this, CaptureActivity::class.java), REQUEST_CODE_SCAN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SCAN -> {
                var bundle = data?.extras
                if (CodeUtils.RESULT_SUCCESS == bundle?.getInt(CodeUtils.RESULT_TYPE)) {
                    toast("解析成功")
                    var jsonParam = bundle.getString(CodeUtils.RESULT_STRING)
                    queryTransaction(jsonParam)
                } else {
                    toast("解析失败")
                }
            }
        }
    }

    private fun queryTransaction(jsonParam: String) {
        LoadingDialog.show(this)
        EthUtils.queryTransaction(jsonParam, TransactionRespResult::class.java, object : ReqCallback<TransactionRespResult> {
            override fun onSuccess(data: TransactionRespResult?) {
                LoadingDialog.dismiss()
                toast(data?.result?.input ?: "查询成功，但没有数据")
            }

            override fun onFailure(msg: String) {
                LoadingDialog.dismiss()
                toast(msg)
            }
        })
    }
}
