package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.BOX_COUNT
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.databinding.ActivityMainBinding
import com.example.fragmentbasicboxgame.databinding.FragmentOptionsBinding

class OptionFragment: Fragment() {

    private lateinit var binding: FragmentOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance(default: Options): Fragment {
            val args :Bundle = Bundle()
            args.putInt(BOX_COUNT,false)
        }
    }


}