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
import com.google.android.material.button.MaterialButton
import com.show.element.AnimatorUtil
import com.show.element.R
import com.show.element.ShareElementInfo

class ReRadius: Transition {

    constructor()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val radius = "ReRadius:radius"

    init {
        addTarget(CardView::class.java)
        addTarget(MaterialButton::class.java)
    }
    override fun getTransitionProperties(): Array<String> {
        return arrayOf(radius)
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
        transitionValues.values[radius] = info.cardRadius
    }

    private fun captureValues(transitionValues: TransitionValues) {
        if(transitionValues.view is CardView){
            transitionValues.values[radius] = (transitionValues.view as CardView).radius
        }else if(transitionValues.view is MaterialButton){
            transitionValues.values[radius] = (transitionValues.view as MaterialButton).cornerRadius
        }
    }

    private fun Any?.toFloat():Float?{
        if(this is Int?){
            return this?.toFloat()
        }else if(this is Float?){
            return this
        }
        return null
    }

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator? {

        val startRadius = startValues.values[radius].toFloat()
        val endRadius = endValues.values[radius].toFloat()



        var animator1: Animator? = null
        if (startRadius != null && endRadius != null && startRadius != endRadius) {
            animator1 = ObjectAnimator.ofFloat(endValues.view, RadiusProperty(), startRadius, endRadius)
        }


        return animator1
    }

    private class RadiusProperty(
        type: Class<Float>? = Float::class.java,
        name: String? = "radius"
    ) :
        Property<View, Float>(type, name) {

        override fun set(view: View?, value: Float) {
            if (view is CardView) {
                view.radius = value
            }else if(view is MaterialButton){
                view.cornerRadius = value.toInt()
            }
        }

        override fun get(view: View?): Float {
            return 0f
        }

    }


}