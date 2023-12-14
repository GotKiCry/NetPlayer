package com.gotkicry.netplayer.util

import android.content.Context
import android.net.Uri
import android.view.SurfaceView
import android.view.TextureView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.InputStream
import java.net.URL

class VLCManager(context: Context, private val textureView: TextureView) {

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
            vlcVout.setVideoView(textureView)
            vlcVout.attachViews()
        }

    }

    fun setMediaPath(mediaPath: String) {
        val media = Media(libVLC, mediaPath)
        mediaPlayer?.media = media
    }

    fun setMediaPath(mediaPath: Uri) {
        val media = Media(libVLC, mediaPath)
        mediaPlayer?.media = media
    }


    fun prepareAndPlay() {
        mediaPlayer?.let {
           if (!it.isPlaying){
               it.play()
           }
            it.setEventListener { event->
                when(event.type){
                    MediaPlayer.Event.Opening->{
                        LogUtil.d("Opening")
                    }
                    MediaPlayer.Event.Buffering->{
                        LogUtil.d("Buffering")
                    }
                    MediaPlayer.Event.Stopped->{
                        LogUtil.d("Stopped")
                    }
                    MediaPlayer.Event.Paused->{
                        LogUtil.d("Paused")
                    }
                    MediaPlayer.Event.Playing->{
                        LogUtil.d("Playing")
                    }
                }
            }
        }
    }

    fun pause(){
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        libVLC?.release()
    }
}