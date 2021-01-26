package com.show.element.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.show.element.AnimatorUtil
import com.show.element.R
import com.show.element.ShareElementInfo


class Recolor : Transition {

    constructor()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val backgroundColor = "Recolor:backgroundColor"
    private val textColor = "Recolor:textColor"

    init {
        addTarget(View::class.java)
        excludeTarget(CardView::class.java, true)
        excludeTarget(Button::class.java, true)
    }

    override fun getTransitionProperties(): Array<String> {
        return arrayOf(backgroundColor, textColor)
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        val view = transitionValues.view
        val info = view.getTag(R.id.tag_transition_extra_properties) as ShareElementInfo?
        if (info != null) {
            captureInfoValues(info, transitionValues)
        } else {
            captureValues(transitionValues)
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
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
    }

    private fun captureValues(transitionValues: TransitionValues) {
        if (transitionValues.view.background is ColorDrawable?) {
            val drawable = transitionValues.view.background as ColorDrawable?
            if (drawable != null) {
                transitionValues.values[backgroundColor] = drawable.color
            }
        }
        if (transitionValues.view is TextView) {
            transitionValues.values[textColor] =
                (transitionValues.view as TextView).currentTextColor
        }
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
        return AnimatorUtil.merge(animator1, animator2)
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