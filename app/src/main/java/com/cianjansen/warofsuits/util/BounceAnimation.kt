package com.cianjansen.warofsuits.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation

object BounceAnimation {
    private const val ANIMATION_BOUNCE_DELAY = 3000L

    private const val ANIMATION_BOUNCE_GROW_SCALE = 1.1f

    private const val ANIMATION_BOUNCE_TIME = 1500L

    private const val ANIMATION_RESET_SCALE = 1f

    fun showBounceAnimation(view: View) {
        val xObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "scaleX",
            ANIMATION_RESET_SCALE,
            ANIMATION_BOUNCE_GROW_SCALE,
            ANIMATION_RESET_SCALE
        )
        xObjectAnimator.repeatCount = Animation.INFINITE

        val yObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "scaleY",
            ANIMATION_RESET_SCALE,
            ANIMATION_BOUNCE_GROW_SCALE,
            ANIMATION_RESET_SCALE
        )
        yObjectAnimator.repeatCount = Animation.INFINITE

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(xObjectAnimator, yObjectAnimator)
        animatorSet.duration = ANIMATION_BOUNCE_TIME
        animatorSet.startDelay = ANIMATION_BOUNCE_DELAY
        animatorSet.start()
    }
}