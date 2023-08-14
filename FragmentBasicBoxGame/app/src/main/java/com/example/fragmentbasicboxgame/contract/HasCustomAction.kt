package com.example.fragmentbasicboxgame.contract

import android.graphics.drawable.Drawable

class CustomAction(val stringRes:Int,val iconRes:Int,val action: Runnable)
interface HasCustomAction {
    fun getActionRes():CustomAction
}