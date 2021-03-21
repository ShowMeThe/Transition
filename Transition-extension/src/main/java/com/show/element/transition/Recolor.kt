package com.show.element.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.util.Log
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.animation.addListener
import com.google.android.material.button.MaterialButton
import com.show.element.AnimatorUtil
import com.show.element.R
import com.show.element.ShareElementInfo


class Recolor : Transition {

    constructor()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val backgroundColor = "Recolor:backgroundColor"
    private val textColor = "Recolor:textColor"
    private val backgroundColorStateList = "Recolor:backgroundColorStateList"

    init {
        addTarget(View::class.java)
    }

    override fun getTransitionProperties(): Array<String> {
        return arrayOf(backgroundColor, textColor, backgroundColorStateList)
    }

    override fun captureStartValues(transitionValues: TransitionValues?) {
        if(transitionValues == null) return
        val view = transitionValues.view
        val info = view.getTag(R.id.tag_transition_extra_properties) as ShareElementInfo?
        if (info != null) {
            captureInfoValues(info, transitionValues)
        } else {
            captureValues(transitionValues)
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues?) {
        if(transitionValues == null) return
        val view = transitionValues.view
        val info = view.getTag(R.id.tag_transition_extra_properties) as ShareElementInfo?
        if (info != null) {
            captureInfoValues(info, transitionValues)
        } else {
            captureValues(transitionValues)
        }
    }

    private fun captureInfoValues(info: ShareElementInfo, transitionValues: TransitionValues) {
        transitionValues.values[backgroundColor] = info.backgroundColor
        transitionValues.values[textColor] = info.textColor
        transitionValues.values[backgroundColorStateList] = info.colorStateList

    }

    private fun captureValues(transitionValues: TransitionValues?) {
        if(transitionValues == null) return
        val view = transitionValues.view
        if (view.background is ColorDrawable?) {
            val drawable = view.background as ColorDrawable?
            if (drawable != null) {
                transitionValues.values[backgroundColor] = drawable.color
            }
        }
        if (view is TextView) {
            transitionValues.values[textColor] = view.currentTextColor
        }
        transitionValues.values[backgroundColorStateList] = view.backgroundTintList
    }

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator? {

        val startColor = startValues.values[backgroundColor] as Int?
        val endColor = endValues.values[backgroundColor] as Int?

        val startTextColor = startValues.values[textColor] as Int?
        val endTextColor = endValues.values[textColor] as Int?

        val startColorStateList = startValues.values[backgroundColorStateList] as ColorStateList?
        val endColorStateList = endValues.values[backgroundColorStateList] as ColorStateList?


        var animator1: Animator? = null
        if (startColor != null && endColor != null && startColor != endColor) {
            animator1 = ObjectAnimator.ofArgb(endValues.view, ColorProperty(), startColor, endColor)
        }

        var animator2: Animator? = null
        if (startTextColor != null && endTextColor != null && startTextColor != endTextColor) {
            animator2 = ObjectAnimator.ofArgb(
                endValues.view,
                TextColorProperty(), startTextColor, endTextColor
            )
        }



        var animator3: Animator? = null
        if (startColorStateList != null && endColorStateList != null && startColorStateList != endColorStateList) {
            animator3 = ObjectAnimator.ofArgb(
                startColorStateList.defaultColor, endColorStateList.defaultColor
            ).apply {
                addUpdateListener {
                    val color = it.animatedValue as Int
                    endValues.view.backgroundTintList = ColorStateList.valueOf(color)
                }
                addListener(
                    onEnd = {
                        endValues.view.backgroundTintList = endColorStateList
                    })
            }
        }

        return AnimatorUtil.merge(animator1, animator2, animator3)
    }





    private class TextColorProperty(
        type: Class<Int>? = Int::class.java,
        name: String? = "textColor"
    ) :
        Property<View, Int>(type, name) {

        override fun set(view: View?, value: Int) {
            if (view is TextView) {
                view.setTextColor(value)
            }
        }

        override fun get(view: View?): Int {
            return 0
        }

    }


    private class ColorProperty(type: Class<Int>? = Int::class.java, name: String? = "background") :
        Property<View, Int>(type, name) {

        override fun set(view: View, value: Int) {
            view.setBackgroundColor(value)
        }

        override fun get(view: View): Int {
            return 0
        }

    }

}