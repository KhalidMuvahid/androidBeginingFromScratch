package com.example.fragmentbasicboxgame.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.fragmentbasicboxgame.Options

fun Fragment.navigator():Navigator{
    return (requireActivity() as Navigator)
}
interface Navigator {

    fun onOpenBox(options: Options)

    fun onOptions(options: Options)

    fun onAbout()

    fun onExit()
    fun goBack()
    fun <T:Parcelable>publishResult(options: T)

    fun <T:Parcelable>listenResult(clazz:Class<T>,owner:LifecycleOwner,listener:ResultListener<T>)

}

typealias ResultListener<T> = (T)->Unit
