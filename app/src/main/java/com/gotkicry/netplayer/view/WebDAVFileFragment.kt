package com.gotkicry.netplayer.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.gotkicry.netplayer.util.LoadingDialog
import com.gotkicry.netplayer.util.ToastUtil
import com.gotkicry.netplayer.viewmodel.WebDAVViewModel
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDavResEvent(davResEvent: DavResEvent) {
        Log.d(TAG,"onGetDavResEvent")
        if (davResEvent.getActionCode() != DavResEvent.ActionCode.CLICK) {
            return
        }
        val davResource = davResEvent.getDavResource()
        if (davResource.isDirectory) {
            webDAVViewModel.getWebDAVFileList(requireActivity(),davResource.path)
        }else{
            ToastUtil.show("打开文件 ${davResource.displayName}")
            val intent = Intent(this.requireContext(),VLCPlayerActivity::class.java)
            intent.putExtra("path",webDAVViewModel.getWebDAVUrl(davResource.path))

            startActivity(intent)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadResult(loadResult: LoadResult){
        if (loadResult.errorCode == LoadResult.TIMEOUT){
            LoadingDialog.hide(requireActivity())
        }
    }

}