package com.show.element

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import kotlinx.android.parcel.Parcelize

@Parcelize
class ShareElementInfo(var snapShot: Parcelable,
                       var backgroundColor: Int? = null,
                       var textColor:Int? = null,
                       var cardRadius:Float? = null
) : Parcelable {

    companion object {

        @JvmStatic
        fun captureViewInfo(snapShot: Parcelable,view: View?): Parcelable {
            var color:Int? = null
            if(view !=null && view.background is ColorDrawable?){
                color = (view.background as ColorDrawable?)?.color
            }
            var textColor :Int? = null
            if(view is TextView){
                textColor = view.currentTextColor
            }
            var radius :Float? = null
            if(view is CardView){
                radius = view.radius
            }else if(view is MaterialButton){
                radius = view.cornerRadius.toFloat()
            }
            return ShareElementInfo(snapShot,color,textColor,radius)
        }
    }


}