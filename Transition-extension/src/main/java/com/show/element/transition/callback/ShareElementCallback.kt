package com.show.element.transition.callback

import androidx.fragment.app.FragmentActivity


fun FragmentActivity.setExtraShareElementCallBack(){
    setExitSharedElementCallback(SharedElementExitCallback())
    setEnterSharedElementCallback(SharedElementEnterCallback())
}