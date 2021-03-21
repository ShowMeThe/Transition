package com.show.element.transition.callback

import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference


fun FragmentActivity.setExtraShareElementCallBack(){
    setExitSharedElementCallback(SharedElementExitCallback())
    setEnterSharedElementCallback(SharedElementEnterCallback())
}

fun Fragment.createSharedElements(@IdRes viewId :Int,transitionName: String = ""):SharedElementsHolder{
    val view = view?.findViewById<View>(viewId)
    val viewRef = WeakReference(view)
    var name = view?.transitionName
    if(transitionName.isNotEmpty()){
        name = transitionName
    }
    return SharedElementsHolder(viewRef,name)
}


fun FragmentActivity.createSharedElements(@IdRes viewId :Int,transitionName: String = ""):SharedElementsHolder{
    val view = this.findViewById<View>(viewId)
    val viewRef = WeakReference(view)
    var name = view.transitionName
    if(transitionName.isNotEmpty()){
        name = transitionName
    }
    return SharedElementsHolder(viewRef,name)
}



data class SharedElementsHolder(val view: WeakReference<View?>,val transitionName: String?)