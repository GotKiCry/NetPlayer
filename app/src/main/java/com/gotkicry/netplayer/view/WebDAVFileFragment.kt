package com.gotkicry.netplayer.view

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotkicry.netplayer.adapter.WebDavRecycleViewAdapter
import com.gotkicry.netplayer.base.BaseFragment
import com.gotkicry.netplayer.databinding.FragmentWebdavFileListBinding
import com.gotkicry.netplayer.event.DavResEvent
import com.gotkicry.netplayer.event.LoadResult
import com.gotkicry.netplayer.event.MsgEvent
import com.gotkicry.netplayer.service.CacheTempVideoService
import com.gotkicry.netplayer.util.LoadingDialog
import com.gotkicry.netplayer.util.LogUtil
import com.gotkicry.netplayer.util.ToastUtil
import com.gotkicry.netplayer.viewmodel.WebDAVViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class WebDAVFileFragment : BaseFragment() {

    companion object{
        private const val TAG = "WebDAVFileFragment"
    }

    private lateinit var webDAVViewModel: WebDAVViewModel

    private lateinit var webdavFileListBinding: FragmentWebdavFileListBinding



    private val webDavListAdapter : WebDavRecycleViewAdapter by lazy { WebDavRecycleViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        webdavFileListBinding = FragmentWebdavFileListBinding.inflate(layoutInflater,container,false)
        webDAVViewModel = ViewModelProvider(this)[WebDAVViewModel::class.java]
        webDAVViewModel.davList.observe(viewLifecycleOwner){
            Log.d(TAG,"show data $it")
            webDavListAdapter.setData(it)
            LoadingDialog.hide(requireActivity())
        }
        webdavFileListBinding.fileList.layoutManager = LinearLayoutManager(context)
        webdavFileListBinding.fileList.adapter = webDavListAdapter

        webDAVViewModel.getWebDAVFileList(requireActivity())


        requireActivity().onBackPressedDispatcher.addCallback(this){
            val data = webDAVViewModel.davList.value
            data?.let {
                Log.d(TAG,"Display Name = ${it[0].displayName}")
                val uri = File(it[0].href.path)
                Log.d(TAG,"URI Parent : ${uri.parent}")
                if (it[0].displayName == "root"){
//                    webDAVViewModel.getWebDAVFileList(requireActivity())
                    requireActivity().finish()
                }else{
                    webDAVViewModel.getWebDAVFileList(requireActivity(),uri.parent)
                }
            }
        }


        return webdavFileListBinding.root
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onGetDavResEvent(davResEvent: DavResEvent) {
        Log.d(TAG,"onGetDavResEvent")
        if (davResEvent.getActionCode() != DavResEvent.ActionCode.CLICK) {
            return
        }
        val davResource = davResEvent.getDavResource()
        if (davResource.isDirectory) {
            webDAVViewModel.getWebDAVFileList(requireActivity(),davResource.path)
        }else{
            val downloadUrl = webDAVViewModel.getDownloadUrl(davResource.path)

            val startVLCIntent = Intent(requireContext(),VLCPlayerActivity::class.java)
            startVLCIntent.putExtra("url",downloadUrl)
            startActivity(startVLCIntent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadResult(loadResult: LoadResult){
        if (loadResult.errorCode == LoadResult.TIMEOUT){
            LoadingDialog.hide(requireActivity())
        }
    }

}