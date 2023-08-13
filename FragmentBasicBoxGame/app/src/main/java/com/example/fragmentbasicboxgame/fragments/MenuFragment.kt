package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.contract.Navigator
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.FragmentMenuBinding

class MenuFragment : Fragment(){

    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater,container,false)
        binding.openBoxButton.setOnClickListener { navigator().onOpenBox() }
        binding.optionsButton.setOnClickListener { navigator().onOptions() }
        binding.aboutButton.setOnClickListener { navigator().onAbout() }
        binding.exitButton.setOnClickListener { navigator().goBack() }
        return binding.root
    }

}