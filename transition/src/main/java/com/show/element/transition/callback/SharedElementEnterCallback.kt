package com.show.element.transition.callback

import android.app.SharedElementCallback
import android.content.Context
import android.os.Parcelable
import android.view.View
import com.show.element.R
import com.show.element.ShareElementInfo

open class SharedElementEnterCallback : SharedElementCallback() {

    override fun onCreateSnapshotView(context: Context?, snapshot: Parcelable?): View? {
        if (snapshot is ShareElementInfo) {
            val view = super.onCreateSnapshotView(context, snapshot.snapShot)
            view?.setTag(R.id.tag_transition_extra_properties,snapshot)
            return view
        }
        return super.onCreateSnapshotView(context, snapshot)
    }

    override fun onSharedElementEnd(
        sharedElementNames: MutableList<String>?,
        sharedElements: MutableList<View>?,
        sharedElementSnapshots: MutableList<View>?
    ) {
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
        if (sharedElements != null && sharedElementSnapshots != null){
            for(index in 0 until sharedElements.size){
                val shareElementView = sharedElements[index]
                shareElementView.setTag(R.id.tag_transition_extra_properties,null)
            }
        }
    }


    override fun onSharedElementStart(
        sharedElementNames: MutableList<String>?,
        sharedElements: MutableList<View>?,
        sharedElementSnapshots: MutableList<View>?
    ) {
        super.onSharedElementStart(
            sharedElementNames,
            sharedElements,
            sharedElementSnapshots
        )
        if (sharedElements != null && sharedElementSnapshots != null){
            for(index in 0 until sharedElements.size){
                val snapShotView = sharedElementSnapshots[index]
                val shareElementView = sharedElements[index]

                val snapShot = snapShotView.getTag(R.id.tag_transition_extra_properties) as ShareElementInfo
                shareElementView.setTag(R.id.tag_transition_extra_properties,snapShot)
            }
        }
    }

}