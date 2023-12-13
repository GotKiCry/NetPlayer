package com.gotkicry.netplayer.model

import android.util.Log
import com.thegrizzlylabs.sardineandroid.DavResource
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class WebDAVRepository {
    companion object{
        private const val TAG = "WebDAVRepository"
    }
    private val okHttpSardine = OkHttpSardine(
        OkHttpClient.Builder()
            .callTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    )


    suspend fun getFileList(webDAVConfig: WebDAVConfig,params:String?=null):MutableList<DavResource>{
        return withContext(Dispatchers.IO){
            okHttpSardine.setCredentials(webDAVConfig.userName,webDAVConfig.password)
            Log.d(TAG,"get webdav Request")
            okHttpSardine.list(webDAVConfig.getConnectURL(params))
        }
    }

}