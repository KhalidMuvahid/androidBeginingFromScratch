package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.BOX_COUNT
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.R
import com.example.fragmentbasicboxgame.TIMER_ENABLE
import com.example.fragmentbasicboxgame.contract.HasCustomTitle
import com.example.fragmentbasicboxgame.databinding.FragmentBoxSelectionBinding

class BoxSelectionFragment:Fragment(),HasCustomTitle {

    private lateinit var binding: FragmentBoxSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentBoxSelectionBinding.inflate(layoutInflater,container,false)
        binding.timerTextView.text = arguments?.getInt(BOX_COUNT).toString()
        return binding.root
    }

    companion object{
        fun newInstance(options: Options): BoxSelectionFragment{
            val args = Bundle()
            args.putInt(BOX_COUNT,options.box)
            args.putBoolean(TIMER_ENABLE,options.isTimerEnable)
            val fragment = BoxSelectionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTitleRes(): Int = R.string.select_box


}