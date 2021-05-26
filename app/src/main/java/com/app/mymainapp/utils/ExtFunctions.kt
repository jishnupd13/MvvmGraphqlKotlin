package com.app.mymainapp.utils

import android.view.View

/** Created by Jishnu P Dileep on 26-05-2021 */


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}
