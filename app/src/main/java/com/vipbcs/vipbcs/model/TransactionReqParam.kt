package com.vipbcs.vipbcs.model

/**
 * Created by wanghao on 2017/9/27.
 */
class TransactionReqParam {
    private var method: String? = null
    private var params: ArrayList<String>? = null
    private var id: String? = null

    constructor(method: String?, params: ArrayList<String>?, id: String?) {
        this.method = method
        this.params = params
        this.id = id
    }
}