package com.example.freshnews

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton constructor(context:Context) {
    companion object{
        @Volatile
        private var INSTANCE:MySingleton?=null
        fun getInstance(context: Context) = INSTANCE ?:synchronized(this){
            INSTANCE ?: MySingleton(context).also {
                INSTANCE =it
            }
        }

    }
    private val requestQeque:RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req : Request<T>){
       requestQeque.add(req)
    }

}