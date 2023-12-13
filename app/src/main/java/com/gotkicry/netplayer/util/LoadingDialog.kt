package com.gotkicry.netplayer.util

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import com.gotkicry.netplayer.R

object LoadingDialog {


    @JvmStatic
    fun show(context: Activity) {
        show(context, null)
    }

    @JvmStatic
    fun show(context: Activity, text: CharSequence?) {
        val viewGroup = context.window.decorView.rootView as ViewGroup
        viewGroup.addView(createLoadingView(context))
    }

    @JvmStatic
    fun hide(context: Activity) {
        val loadingView = context.window.decorView.rootView.findViewById<View>(R.id.loading_root)
        val parent = context.window.decorView.rootView as ViewGroup
        parent.removeView(loadingView)

    }

    private fun createLoadingView(context: Activity): View {
        val frameLayout = FrameLayout(context)
        frameLayout.id = R.id.loading_root

        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.gravity = Gravity.CENTER
        frameLayout.layoutParams = layoutParams
        frameLayout.setBackgroundColor(Color.argb(200, 20, 20, 20))
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.loading)
        imageView.layoutParams = FrameLayout.LayoutParams(200, 200, Gravity.CENTER)
        val rotateAnimation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 600
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatMode = Animation.RESTART
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.fillAfter = true
        imageView.animation = rotateAnimation
        rotateAnimation.start()

        frameLayout.addView(imageView)
        frameLayout.setOnTouchListener { v, event -> true }

        return frameLayout
    }


}