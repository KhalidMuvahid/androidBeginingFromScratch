package com.example.fragmentbasicboxgame.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.Options
import com.example.fragmentbasicboxgame.R
import com.example.fragmentbasicboxgame.contract.HasCustomTitle
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.FragmentBoxSelectionBinding
import com.example.fragmentbasicboxgame.databinding.ItemBoxBinding
import kotlin.math.max
import kotlin.properties.Delegates
import kotlin.random.Random

class BoxSelectionFragment:Fragment(),HasCustomTitle{

    private lateinit var binding: FragmentBoxSelectionBinding
    private lateinit var options: Options

    private var timerStartTimestamp by Delegates.notNull<Long>()
    private var boxIndex by Delegates.notNull<Int>()
    private var alreadyDone = false

    private  var timerHandler:TimerHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = arguments?.getParcelable(ARG_OPTIONS) ?:
                throw IllegalArgumentException("Can't launch BoxSelectionActivity without options")

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.box)

        timerHandler = if (options.isTimerEnable){
            TimerHandler()
        }else{
            null
        }

        timerHandler?.onCreate(savedInstanceState)
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

    override fun onStart() {
        super.onStart()
        timerHandler?.onStart()
    }

    override fun onStop() {
        super.onStop()
        timerHandler?.onStop()
    }




    private fun createBox() {
        val boxBindings = (0..options.box).map { index->
            val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.boxTitleTextView.text= getString(R.string.box_title,index+1)
            boxBinding.root.setOnClickListener { view -> onBoxSelected(view) }
            boxBinding.root.tag = index
            binding.root.addView(boxBinding.root)
            boxBinding

        }
        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()

    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex){
            alreadyDone = true
            navigator().showCongratulationsScreen()
        }else{
            Toast.makeText(context,R.string.empty_box,Toast.LENGTH_SHORT).show()
        }
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

    inner class TimerHandler {

        private var timer:CountDownTimer? = null
        fun onCreate(savedInstanceState: Bundle?){
            timerStartTimestamp = savedInstanceState?.getLong(KEY_START_TIMESTAMP)
                ?: System.currentTimeMillis()
            alreadyDone = savedInstanceState?.getBoolean(KEY_ALREADY_DONE) ?: false
        }

        fun onSaveInstantState(outState: Bundle){
            outState.putLong(KEY_START_TIMESTAMP,timerStartTimestamp)
            outState.putBoolean(KEY_ALREADY_DONE,alreadyDone)
        }

        fun onStart(){
            if (alreadyDone) return

            timer = object :CountDownTimer(getRemainingSeconds()*1000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    updateTimerUi()
                }

                override fun onFinish() {
                    updateTimerUi()
                    showTimerDialog()
                }
            }
            updateTimerUi()
            timer?.start()
        }

        fun onStop(){
            timer?.cancel()
            timer = null
        }


    }

    private fun updateTimerUi() {
        if (getRemainingSeconds() >= 0){
            binding.timerTextView.visibility = View.VISIBLE
            binding.timerTextView.text = getString(R.string.timer_value,getRemainingSeconds())
        }else{
            binding.timerTextView.visibility = View.GONE
        }
    }

    private fun getRemainingSeconds(): Long {
        val finishedAt = timerStartTimestamp+ TIMER_DURATION
        return max(0,(finishedAt - System.currentTimeMillis())/1000)
    }
    private fun showTimerDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Timer")
        dialog.setMessage("You are lose.Time ended.")
        dialog.setCancelable(false)
        dialog.setPositiveButton(android.R.string.ok){_,_-> navigator().goBack()}
        dialog.create()
        dialog.show()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_INDEX,options)
        timerHandler?.onSaveInstantState(outState)
    }
}