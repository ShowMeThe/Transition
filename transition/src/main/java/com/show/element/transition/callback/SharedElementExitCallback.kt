package com.show.element.transition.callback

import android.app.SharedElementCallback
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.view.View
import com.show.element.ShareElementInfo

open class SharedElementExitCallback : SharedElementCallback() {

    override fun onCaptureSharedElementSnapshot(
        sharedElement: View?,
        viewToGlobalMatrix: Matrix?,
        screenBounds: RectF?
    ): Parcelable {
        return ShareElementInfo.captureViewInfo(
            super.onCaptureSharedElementSnapshot(
                sharedElement,
                viewToGlobalMatrix,
                screenBounds
            ), sharedElement
        )
    }

}