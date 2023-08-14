package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.FragmentCongratulationsBinding

class CongratulationsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCongratulationsBinding.inflate(layoutInflater,container,false).apply {
        toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
    }.root

    private fun onToMainMenuPressed(){
        navigator().goBack()
    }
}