package com.developer.nfcproject.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.core.view.children

fun View.showView(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.hideKeypad() {
    val inputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showWithSlideAnimation() {
    val slideUpAnim = ObjectAnimator.ofFloat(
        this,
        "translationY",
        this.height.toFloat(),
        0F
    ).apply {
        duration = 500
    }

    val slideDownAnim = ObjectAnimator.ofFloat(
        this,
        "translationY",
        0F,
        this.height.toFloat()
    ).apply {
        duration = 500
        startDelay = 3000
    }

    AnimatorSet().apply {
        play(slideUpAnim).before(slideDownAnim)
        addListener(onEnd = {
            showView(false)
            this.removeAllListeners()
        }, onStart = {
            showView(true)
        })
        start()
    }
}

fun ViewGroup.enableChildren(enable: Boolean) {
    this.children.forEach { view ->
        view.isEnabled = enable
    }
}
