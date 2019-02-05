package com.josamuna.smartmanagerest.interfaces

import android.support.v4.app.Fragment

interface ISharedFragment {
    /**
     * Allow to custom a open Fragment when pass Fragment instance and his Id
     */
    fun openFragment(fragment: Fragment, fragment_id: Int)
}