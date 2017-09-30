package com.vipbcs.vipbcs.utils

import android.util.Log
import com.google.gson.Gson
import com.vipbcs.vipbcs.model.ReqCallback
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by wanghao on 2017/9/27.
 */
object OkHttpUtils {
    val TAG = "OkHttpUtils"
    val TIME_OUT: Long = 10
    var client: OkHttpClient? = null

    init {
        var builder = OkHttpClient.Builder()

        //设置超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        //生成OkHttpClient对象
        builder.followRedirects(true)
        client = builder.build()
    }

    fun <T> get(url: String, clazz: Class<T>, callback: ReqCallback<T>) {
        var request = Request.Builder().get().url(url).build()
        var call = client?.newCall(request)
        call?.enqueue(genCallback(clazz, callback))
    }

    fun <T> post(url: String, mapData: TreeMap<String, String>, clazz: Class<T>, callback: ReqCallback<T>) {
        var builder = FormBody.Builder()
        for (keyPair in mapData) {
            builder.add(keyPair.key, keyPair.value)
        }
        var body = builder.build()
        var request = Request.Builder().post(body).url(url).build()
        var call = client?.newCall(request)
        call?.enqueue(genCallback(clazz, callback))
    }

    fun <T> post(url: String, jsonParam: String?, clazz: Class<T>, callback: ReqCallback<T>) {
        var JSON = MediaType.parse("application/json; charset=utf-8")
        var body = RequestBody.create(JSON, jsonParam ?: "")
        var request = Request.Builder().post(body).url(url).build()
        var call = client?.newCall(request)
        call?.enqueue(genCallback(clazz, callback))
    }


    private fun <T> genCallback(clazz: Class<T>, callback: ReqCallback<T>): Callback {
        return object : Callback {
            override fun onFailure(p0: Call?, p1: IOException?) {
                failureCallback(p1?.toString() ?: "网络异常", callback)
            }

            override fun onResponse(p0: Call?, p1: Response?) {
                var content = p1?.body()?.string()
                var result: T? = null
                if (!content.isNullOrEmpty()) {
                    try {
                        result = Gson().fromJson(content, clazz)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    }
                }
                successCallback(result, callback)
            }
        }
    }

    fun <T> successCallback(data: T?, callback: ReqCallback<T>) {
        AsyncThread.getInstance()?.postToMainThreadDelayed(Runnable {
            callback.onSuccess(data)
        }, 2000)
    }

    fun <T> failureCallback(msg: String, callback: ReqCallback<T>) {
        AsyncThread.getInstance()?.postToMainThreadDelayed(Runnable {
            callback.onFailure(msg)
        },2000)
    }
}