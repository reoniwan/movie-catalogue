package com.frozenproject.moviecatalogue.internal

import kotlinx.coroutines.*


fun <T> lazyDefferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}