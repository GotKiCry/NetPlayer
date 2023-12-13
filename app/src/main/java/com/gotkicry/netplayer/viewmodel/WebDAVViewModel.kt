package com.gotkicry.netplayer.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotkicry.netplayer.event.LoadResult
import com.gotkicry.netplayer.model.WebDAVConfig
import com.gotkicry.netplayer.model.WebDAVRepository
import com.gotkicry.netplayer.util.LoadingDialog
import com.gotkicry.netplayer.util.ToastUtil
import com.thegrizzlylabs.sardineandroid.DavResource
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class WebDAVViewModel() : ViewModel() {
    companion object{
        private const val TAG = "WebDAVViewModel"
    }

    private val webDAVRepository by lazy { WebDAVRepository() }

    private val _data = MutableLiveData<MutableList<DavResource>>()

    val davList : LiveData<MutableList<DavResource>> = _data

    init {
        _data.value = mutableListOf()
    }


    private fun getDefConfig():WebDAVConfig{
        return WebDAVConfig(
            userName = "lzh",
            password = "lzh1103.",
            isEnableSSL = true,
            baseURL = "gotkicry.com",
            suffix = "dav"
        )
    }

    fun getWebDAVFileList(activity: Activity,params:String? = null){
        viewModelScope.launch {
            LoadingDialog.show(activity)
            try {
                _data.value = webDAVRepository.getFileList(getDefConfig(),params)
            }catch (e : IOException){
                if(e.message == "Not a valid DAV response"){
                    ToastUtil.show("获取列表超时")
                }
                EventBus.getDefault().post(LoadResult(LoadResult.TIMEOUT,e.message))
            }
            Log.d(TAG,"_data.value : ${_data.value}")
        }
    }

    fun getWebDAVUrl(params:String? = null):String{
       return getDefConfig().getConnectURL(params)
    }

}