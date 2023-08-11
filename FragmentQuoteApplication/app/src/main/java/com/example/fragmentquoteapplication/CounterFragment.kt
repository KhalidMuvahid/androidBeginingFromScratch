package com.example.fragmentquoteapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentquoteapplication.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCounterBinding.inflate(layoutInflater,container,false)

        binding.ScreenTitle.text = getString(R.string.screen,getCount())
        binding.quote.text = getQuote()

        binding.next.setOnClickListener { launchNext() }
        binding.back.setOnClickListener { goBack() }

        return binding.root
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    private fun launchNext() {
        val fragment  = newInstance(
            counterValue = (requireActivity() as MainActivity).getScreenCount()+1,
            quote = (requireActivity() as MainActivity).createQuote())

        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    private fun getCount():Int{
       return requireArguments().getInt(ARG_COUNTER)
    }

    fun getQuote():String{
        return requireArguments().getString(ARG_COUNTER_VALUE)!!
    }

    companion object{
        private const val ARG_COUNTER_VALUE = "ARG_COUNTER_VALUE";
        private const val ARG_COUNTER = "ARG_COUNTER";

        fun newInstance(counterValue:Int,quote:String): CounterFragment{
            val args = Bundle()
            args.putInt(ARG_COUNTER,counterValue)
            args.putString(ARG_COUNTER_VALUE,quote)
            val fragment = CounterFragment()
            fragment.arguments = args
            return fragment
        }
    }

}