package com.vipbcs.vipbcs.model

/**
 * Created by wanghao on 2017/9/27.
 */
class TransactionRespResult {
    var jsonrpc: String? = null
    var id: String? = null
    var result: Transaction? = null

    class Transaction {
        var hash: String? = null
        var nonce: String? = null
        var blockHash: String? = null
        var blockNumber: String? = null
        var transactionIndex: String? = null
        var from: String? = null
        var to: String? = null
        var value: String? = null
        var gasPrice: String? = null
        var gas: String? = null
        var input: String? = null
        var creates: String? = null
        var publicKey: String? = null
        var raw: String? = null
        var r: String? = null
        var s: String? = null
        var v: String? = null
    }
}