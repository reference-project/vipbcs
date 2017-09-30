package com.vipbcs.vipbcs.utils

import com.google.gson.Gson
import com.vipbcs.vipbcs.model.ReqCallback
import com.vipbcs.vipbcs.model.TransactionReqParam

/**
 * Created by wanghao on 2017/9/27.
 */
object EthUtils {
    private val URL = "http://192.168.145.243:8545/"


    fun <T> queryTransaction(param: TransactionReqParam, clazz: Class<T>, callback: ReqCallback<T>) {
        var jsonParam: String? = null
        if (param != null) {
            jsonParam = Gson().toJson(param)
        }
        OkHttpUtils.post(URL, jsonParam, clazz, callback)
    }

    fun <T> queryTransaction(jsonParam: String, clazz: Class<T>, callback: ReqCallback<T>) {
        OkHttpUtils.post(URL, jsonParam, clazz, callback)
    }

}