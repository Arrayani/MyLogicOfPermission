package com.gamecodeschool.mylogicofpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var mLayout :View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnbolehakses = findViewById<Button>(R.id.btnPermission)

        btnbolehakses.setOnClickListener { cekForPermision() }

    }

    private fun cekForPermision() {
        mLayout = findViewById(R.id.rootLayout)
        //if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin Bluetooth", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses",Snackbar.LENGTH_LONG).show()
            requestBluetoothAdminPermission()
        }
    }

    private fun requestBluetoothAdminPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA

            )){
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.CAMERA)),
                    PERMISSION_REQUEST_BLUETOOTH_ADMIN
                )
        }else{    ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf((Manifest.permission.CAMERA)),
            PERMISSION_REQUEST_BLUETOOTH_ADMIN
        )

        }
    }
    companion object {
        private const val PERMISSION_REQUEST_BLUETOOTH_ADMIN = 0
    }
}