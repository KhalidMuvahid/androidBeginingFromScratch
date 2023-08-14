package com.example.fragmentbasicboxgame

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.fragmentbasicboxgame.contract.CustomAction
import com.example.fragmentbasicboxgame.contract.HasCustomAction
import com.example.fragmentbasicboxgame.contract.HasCustomTitle
import com.example.fragmentbasicboxgame.contract.Navigator
import com.example.fragmentbasicboxgame.contract.ResultListener
import com.example.fragmentbasicboxgame.databinding.ActivityMainBinding
import com.example.fragmentbasicboxgame.fragments.AboutFragment
import com.example.fragmentbasicboxgame.fragments.BoxSelectionFragment
import com.example.fragmentbasicboxgame.fragments.MenuFragment
import com.example.fragmentbasicboxgame.fragments.OptionFragment
class MainActivity : AppCompatActivity(),Navigator {

    private lateinit var binding:ActivityMainBinding

    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!

    private val fragmentListener = object :FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUI()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,MenuFragment())
                .commit()
        }


        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener,false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        updateUI()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOpenBox(options: Options) {
        launchFragment(BoxSelectionFragment.newInstance(options))
    }

    override fun onOptions(options: Options) {
        val fragment = OptionFragment.newInstance(options)
        updateUI()
        launchFragment(fragment)
    }

    override fun onAbout() {
        val fragment = AboutFragment()
        updateUI()
        launchFragment(fragment)
    }

    override fun onExit() {
        TODO("Not yet implemented")
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun <T : Parcelable> publishResult(options: T) {
        supportFragmentManager.setFragmentResult(options.javaClass.name, bundleOf(KEY_RESULT to options))
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(clazz.name,owner, FragmentResultListener { key,bundle ->
            listener.invoke(bundle.getParcelable(KEY_RESULT)!!)
        })
    }


    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null).
            replace(R.id.fragment_container,fragment)
            .commit()
    }

    private fun updateUI(){
        val fragment = currentFragment

        if (fragment is HasCustomTitle){
            binding.toolbar.title = resources.getString(fragment.getTitleRes())
        }else{
            binding.toolbar.title = resources.getString(R.string.app_name)
        }

        if(supportFragmentManager.backStackEntryCount>0){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }

        if (fragment is HasCustomAction){
            createMenu(fragment.getActionRes())
        }else{
            binding.toolbar.menu.clear()
        }
    }

   private fun createMenu(action:CustomAction){
       binding.toolbar.menu.clear()
       val icon = resources.getDrawable(action.iconRes)

       val menuItem = binding.toolbar.menu.add(action.stringRes)
       menuItem.icon = icon
       menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
       menuItem.setOnMenuItemClickListener {
           action.action.run()
           return@setOnMenuItemClickListener true
       }

   }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    companion object {
        @JvmStatic private val KEY_RESULT = "RESULT"
    }
}