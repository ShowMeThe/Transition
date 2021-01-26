package com.show.element

import android.animation.Animator
import android.animation.AnimatorSet

object AnimatorUtil {


    fun merge(animator1: Animator? = null,animator2:Animator? = null):AnimatorSet?{
        val animatorSet =  AnimatorSet()
        val list = arrayListOf<Animator>()
        animator1?.apply { list.add(animator1) }
        animator2?.apply { list.add(animator2) }
        animatorSet.playTogether(list)
        return animatorSet
    }




}