package com.gotkicry.netplayer.event

import com.thegrizzlylabs.sardineandroid.DavResource

data class DavResEvent(
    private val actionCode : ActionCode,
    private val davResource: DavResource
){

    enum class ActionCode{
        CLICK,OPEN
    }

    fun getActionCode() = actionCode

    fun getDavResource() = davResource
}
