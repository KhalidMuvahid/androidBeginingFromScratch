package com.example.fragmentbasicboxgame

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.contract.Navigator
import com.example.fragmentbasicboxgame.fragments.AboutFragment
import com.example.fragmentbasicboxgame.fragments.BoxSelectionFragment
import com.example.fragmentbasicboxgame.fragments.MenuFragment
import com.example.fragmentbasicboxgame.fragments.OptionFragment

const val BOX_COUNT = "BOX_COUNT"
const val TIMER_ENABLE = "TIMER_ENABLE"
class MainActivity : AppCompatActivity(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragment_container,MenuFragment()).commit()
    }

    override fun onOpenBox() {
        launchFragment(BoxSelectionFragment.newInstance(Options.Default))
    }

    override fun onOptions() {
        launchFragment(OptionFragment.newInstance(Options.Default))
    }

    override fun onAbout() {
        launchFragment(AboutFragment())
    }

    override fun onExit() {
        TODO("Not yet implemented")
    }

    override fun goBack() {
        onBackPressed()
    }


    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null).
            replace(R.id.fragment_container,fragment)
            .commit()
    }
}