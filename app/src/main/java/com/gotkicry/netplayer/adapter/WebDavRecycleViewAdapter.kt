package com.gotkicry.netplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gotkicry.netplayer.databinding.ItemDavFileBinding
import com.gotkicry.netplayer.event.DavResEvent
import com.thegrizzlylabs.sardineandroid.DavResource
import org.greenrobot.eventbus.EventBus

class WebDavRecycleViewAdapter : RecyclerView.Adapter<WebDavRecycleViewAdapter.WebDavRecycleViewViewHolder>() {


    private val davResources : MutableList<DavResource> by lazy { mutableListOf() }

    class WebDavRecycleViewViewHolder(private val viewBinding: ItemDavFileBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun setFileName(name:String){
            viewBinding.tvFileName.text = name
        }

        fun setOnClick(onClick:()->Unit){
            viewBinding.tvFileName.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebDavRecycleViewViewHolder {
        val itemDavListViewBinding = ItemDavFileBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WebDavRecycleViewViewHolder(itemDavListViewBinding)
    }

    override fun getItemCount(): Int {
        return davResources.count() - 1
    }

    override fun onBindViewHolder(holder: WebDavRecycleViewViewHolder, position: Int) {
        val davResource = davResources[position + 1]
        holder.setFileName(davResource.displayName)
        holder.setOnClick {
            EventBus.getDefault().post(DavResEvent(DavResEvent.ActionCode.CLICK,davResource))
        }
    }

    fun setData(davResources: List<DavResource>){
        this.davResources.clear()
        this.davResources.addAll(davResources)
        notifyDataSetChanged()
    }
}