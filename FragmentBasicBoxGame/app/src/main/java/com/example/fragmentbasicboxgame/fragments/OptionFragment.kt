package com.example.fragmentbasicboxgame.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.R
import com.example.fragmentbasicboxgame.contract.CustomAction
import com.example.fragmentbasicboxgame.contract.HasCustomAction
import com.example.fragmentbasicboxgame.contract.HasCustomTitle
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.FragmentOptionsBinding
import java.lang.IllegalArgumentException

class OptionFragment: Fragment(),HasCustomAction,HasCustomTitle {

    private lateinit var binding: FragmentOptionsBinding
    private lateinit var options:Options
    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        options = savedInstanceState?.getParcelable<Options>(KEY_OPTIONS) ?:
                arguments?.getParcelable(ARG_OPTIONS) ?:
                throw IllegalArgumentException("You need to specify options to launch this fragment")


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater,container,false)

        setupSpinner()
        setupCheckBox()
        updateUI()


        binding.cancelButton.setOnClickListener { onCancelPressed() }
        binding.confirmButton.setOnClickListener { onConfirmPressed() }
        return binding.root
    }

    private fun onConfirmPressed() {
        navigator().publishResult(options)
        navigator().goBack()
    }

    private fun onCancelPressed() {
        navigator().goBack()
    }

    private fun updateUI() {
        binding.enableTimerCheckBox.isChecked = options.isTimerEnable

        val currentIndex = boxCountItems.indexOfFirst { it.count == options.box }
        binding.boxCountSpinner.setSelection(currentIndex)
    }

    private fun setupCheckBox() {
        binding.enableTimerCheckBox.setOnClickListener {
            options = options.copy(isTimerEnable = binding.enableTimerCheckBox.isChecked)
        }
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map { BoxCountItem(it,resources.getQuantityString(R.plurals.boxes,it,it)) }
        adapter = ArrayAdapter(requireContext(),
            R.layout.item_spinner,
            boxCountItems)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val count = boxCountItems[position].count
                options = options.copy(box = count)
            }
        }

    }

    companion object {

        private val ARG_OPTIONS = "ARG_OPTIONS"
        private val KEY_OPTIONS = "KEY_OPTIONS"
        fun newInstance(options: Options): Fragment {
            val args :Bundle = Bundle()
            args.putParcelable(ARG_OPTIONS,options)
            val fragment = OptionFragment()
            fragment.arguments  = args
            return fragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS,options)
    }

    class BoxCountItem(val count:Int,private val optionTitle:String){
        override fun toString(): String {
            return optionTitle
        }
    }

    override fun getActionRes(): CustomAction {
        return CustomAction(R.string.confirm,
            R.drawable.ic_done,
            object :Runnable{
                override fun run() {
                    onConfirmPressed()
                }

            })
    }

    override fun getTitleRes(): Int = R.string.options


}