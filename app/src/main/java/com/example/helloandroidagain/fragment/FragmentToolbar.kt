package com.example.helloandroidagain.fragment

import androidx.annotation.DrawableRes


interface FragmentToolbar {
    fun getFragmentTitle(): String //todo: change to res Id
    fun getFragmentAction(): FragmentAction
}

class FragmentAction(
    @DrawableRes
    val actionIcRes: Int,
    val actionHint: String, //todo: change to res Id
    val onFragmentAction: Runnable
)