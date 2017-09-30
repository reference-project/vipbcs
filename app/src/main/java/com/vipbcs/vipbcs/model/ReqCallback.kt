package com.vipbcs.vipbcs.model

/**
 * Created by wanghao on 2017/9/27.
 */
interface ReqCallback<T> {

    fun onSuccess(data: T?)

    fun onFailure(msg: String)
}