package com.vipbcs.vipbcs.utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

/**
 * Created by wanghao on 2017/9/27.
 */
class AsyncThread private constructor() {
    private var mTaskHandler: Handler? = null
    private var mMainLooperHandler: Handler? = null
    private val HANDLE_THREAD_NAME = "HANDLE_THREAD_NAME"

    init {
        val handlerThread = HandlerThread(HANDLE_THREAD_NAME)
        handlerThread.start()
        mTaskHandler = Handler(handlerThread.looper)
        mMainLooperHandler = Handler(Looper.getMainLooper())
    }

    companion object {
        @Volatile private var mInstance: AsyncThread? = null
        fun getInstance(): AsyncThread? {
            return if (mInstance == null) {
                synchronized(AsyncThread::class.java) {
                    if (mInstance == null) {
                        mInstance = AsyncThread()
                    }
                    return mInstance
                }
            } else {
                mInstance
            }
        }
    }

    fun postToWorker(task: Runnable) {
        mTaskHandler?.post(task)
    }

    fun postToWorkerDelayed(task: Runnable, delayInMs: Long) {
        mTaskHandler?.postDelayed(task, delayInMs)
    }

    fun postToMainThread(task: Runnable) {
        mMainLooperHandler?.post(task)
    }

    fun postToMainThreadDelayed(task: Runnable, delayInMs: Long) {
        mMainLooperHandler?.postDelayed(task, delayInMs)
    }

}