package com.example.fragmentquoteapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.fragmentquoteapplication.databinding.ActivityMainBinding
import com.github.javafaker.Faker
import java.util.*

class MainActivity : AppCompatActivity() {
    private val faker = Faker.instance()
    private lateinit var  binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

       if (savedInstanceState == null){
           val  fragment  = CounterFragment.newInstance(getScreenCount(),createQuote())

           supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,fragment).commit()
       }
    }

    fun getScreenCount() = supportFragmentManager.backStackEntryCount+1;
    fun createQuote() = faker.harryPotter().quote()
}