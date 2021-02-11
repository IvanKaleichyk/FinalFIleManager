package com.koleychik.finalfilemanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.koleychik.finalfilemanager.navigation.Navigator
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FinalFIleManager)
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        navigator.bind(controller)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.unbind()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        setContentView(R.layout.activity_main)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, R.string.cannot_without_permissions, Toast.LENGTH_LONG)
            .show()
        finish()
    }
}