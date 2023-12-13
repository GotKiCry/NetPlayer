package com.gotkicry.netplayer.util

import android.content.Context
import android.view.SurfaceView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.InputStream

class VLCManager(context: Context, private val surfaceView: SurfaceView) {

    private var libVLC: LibVLC? = null
    private var mediaPlayer: MediaPlayer? = null

    init {
        initializeLibVLC(context)
        initializeMediaPlayer()
    }

    private fun initializeLibVLC(context: Context) {
        val args = ArrayList<String>()
        args.add("--no-xlib")
        libVLC = LibVLC(context, args)
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer(libVLC)

        mediaPlayer?.let {
            it.volume = 0
            val vlcVout = it.vlcVout
            vlcVout.setVideoView(surfaceView)
        }

    }

    fun setMediaPath(mediaPath: String) {
        val media = Media(libVLC, mediaPath)
        mediaPlayer?.media = media
    }


    fun prepareAndPlay() {
        mediaPlayer?.play()
    }

    fun release() {
        mediaPlayer?.release()
        libVLC?.release()
    }
}