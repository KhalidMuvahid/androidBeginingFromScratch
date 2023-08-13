package com.example.fragmentbasicboxgame.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentbasicboxgame.BuildConfig
import com.example.fragmentbasicboxgame.contract.navigator
import com.example.fragmentbasicboxgame.databinding.ActivityAboutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) :View? = ActivityAboutBinding.inflate(layoutInflater,container,false).apply {
            versionNameTextView.text = BuildConfig.VERSION_NAME
            versionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
            okButton.setOnClickListener{navigator().goBack()}
        }.root

}