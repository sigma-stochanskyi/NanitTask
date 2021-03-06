package com.stochanskyi.nanittask.presentation.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

typealias SingleLiveAction = SingleLiveEvent<Unit>

class SingleLiveEvent<T> : MutableLiveData<T> {

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    private val mPending = AtomicBoolean(false)

    constructor() : super()

    constructor(@Nullable value: T?) : super() {
        setValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner) {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }

    @MainThread
    fun postCall() {
        postValue(null)
    }
}