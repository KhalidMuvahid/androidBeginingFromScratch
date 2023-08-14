package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.R
import com.example.fragmentbasicboxgame.contract.HasCustomTitle
import com.example.fragmentbasicboxgame.databinding.FragmentBoxSelectionBinding
import com.example.fragmentbasicboxgame.databinding.ItemBoxBinding

class BoxSelectionFragment:Fragment(),HasCustomTitle {

    private lateinit var binding: FragmentBoxSelectionBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_INDEX) ?: arguments?.getParcelable(
            ARG_OPTIONS)!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentBoxSelectionBinding.inflate(layoutInflater,container,false)

        createBox()

        return binding.root
    }

    private fun createBox() {
        val boxBindings = (0..options.box).map { index->
            val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.boxTitleTextView.text= getString(R.string.box_title,index+1)
            boxBinding.root.setOnClickListener { view -> TODO() }
            boxBinding.root.tag = index
            binding.root.addView(boxBinding.root)
            boxBinding

        }
        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()

    }

    companion object{

        @JvmStatic private val ARG_OPTIONS = "EXTRA_OPTIONS"
        @JvmStatic private val KEY_INDEX = "KEY_INDEX"
        @JvmStatic private val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"
        @JvmStatic private val KEY_ALREADY_DONE = "KEY_ALREADY_DONE"
        @JvmStatic private val TIMER_DURATION = 10_000L
        fun newInstance(options: Options): BoxSelectionFragment{
            val args = Bundle()
            args.putParcelable(ARG_OPTIONS,options)
            val fragment = BoxSelectionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getTitleRes(): Int = R.string.select_box


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_INDEX,options)
    }
}