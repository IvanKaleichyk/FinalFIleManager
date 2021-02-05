package com.koleychik.finalfilemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.koleychik.finalfilemanager.navigation.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FileManagerApplication.component.inject(this)
        navigator.bind(controller)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.unbind()
    }
}