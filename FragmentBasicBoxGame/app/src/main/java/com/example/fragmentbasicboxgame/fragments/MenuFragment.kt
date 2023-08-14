package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.contract.Navigator
import com.example.fragmentbasicboxgame.contract.ResultListener
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.FragmentMenuBinding

class MenuFragment : Fragment(){

    private lateinit var binding: FragmentMenuBinding
    private lateinit var options:Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.Default
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        navigator().listenResult(Options::class.java,viewLifecycleOwner){this.options=it}
        binding.openBoxButton.setOnClickListener { navigator().onOpenBox(options) }
        binding.optionsButton.setOnClickListener { navigator().onOptions(options) }
        binding.aboutButton.setOnClickListener { navigator().onAbout() }
        binding.exitButton.setOnClickListener { navigator().goBack() }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS,options)
    }
    companion object {
        @JvmStatic private val KEY_OPTIONS = "OPTIONS"
    }

}