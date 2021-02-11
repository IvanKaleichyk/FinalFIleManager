package com.koleychik.finalfilemanager

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koleychik.finalfilemanager.navigation.Navigator
import com.koleychik.module_injector.AppConstants
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var navigator: Navigator

    private val controller by lazy {
        findNavController(R.id.navController)
    }

    private val tag = "MAIN_APP_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FinalFIleManager)
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        checkPermission()
    }

    private fun startActivity() {
        setContentView(R.layout.activity_main)
        navigator.bind(controller)
    }


    override fun onDestroy() {
        super.onDestroy()
        navigator.unbind()
    }

    @AfterPermissionGranted(123)
    private fun checkPermission() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(applicationContext, *permissions)) startActivity()
        else {
            EasyPermissions.requestPermissions(
                this,
                applicationContext.getString(R.string.cannot_without_permissions),
                123,
                *permissions
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(tag, "onRequestPermissionsResult")
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(tag, "onPermissionsGranted")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, R.string.cannot_without_permissions, Toast.LENGTH_LONG)
            .show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Log.d(AppConstants.TAG, "onActivityResult")
        }
    }
}
