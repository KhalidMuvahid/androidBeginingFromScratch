package com.example.fragmentbasicboxgame

import android.os.Parcelable
import android.support.v4.os.IResultReceiver.Default
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Options(val box:Int,val isTimerEnable:Boolean) : Parcelable {

    companion object{
        val Default:Options = Options(3,false)
    }

}