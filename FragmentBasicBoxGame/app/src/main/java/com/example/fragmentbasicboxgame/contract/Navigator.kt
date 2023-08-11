package com.example.fragmentbasicboxgame.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator():Navigator{
    return (requireActivity() as Navigator)
}
interface Navigator {

    fun onOpenBox()

    fun onOptions()

    fun onAbout()

    fun onExit()
    fun goBack()

}