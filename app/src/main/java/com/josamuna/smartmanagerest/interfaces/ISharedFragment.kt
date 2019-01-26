package com.josamuna.smartmanagerest.interfaces

import android.support.v4.app.Fragment

interface ISharedFragment {
    fun openFragment(fragment: Fragment, fragment_id: Int)
}