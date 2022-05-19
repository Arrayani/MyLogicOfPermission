package com.gamecodeschool.mylogicofpermission

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var bAdapter :BluetoothAdapter
    private val REQUEST_CODE_ENABLE_BT:Int =1
    lateinit var mLayout :View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnBluetooth = findViewById<Button>(R.id.btnBluetooth)
        val btnConnPermit = findViewById<Button>(R.id.btnConnPermit)
        val btnAdminPermit =  findViewById<Button>(R.id.btnBtAdminPermit)
        val btnBtScanPermit = findViewById<Button>(R.id.btnBtScanPermit)
        val turnOnBtn= findViewById<Button>(R.id.turnOnBtn)

        //init bluetooth

        bAdapter = BluetoothAdapter.getDefaultAdapter()

//        val bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        bluetoothManager.getAdapter()

        btnBluetooth.setOnClickListener { cekBtPermission() }
        btnConnPermit.setOnClickListener { cekForPermision() }
        btnAdminPermit.setOnClickListener { cekAdminBTPermission() }
        btnBtScanPermit.setOnClickListener { cekScanBTPermission() }

        turnOnBtn.setOnClickListener {
            if (bAdapter.isEnabled){
                Toast.makeText(this,"already on", Toast.LENGTH_LONG).show()
            }
            else{
                //cekForPermision()
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent,REQUEST_CODE_ENABLE_BT)
            }

        }

    }

    private fun cekScanBTPermission() {
        mLayout= findViewById(R.id.rootLayout)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_SCAN)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin akses Admin Bluetooth", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses Admin Bluetooth",Snackbar.LENGTH_LONG).show()
            requestScanBTPermission()
        }
    }

    private fun requestScanBTPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.BLUETOOTH_SCAN

            )){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_SCAN)),
                    PERMISSION_REQUEST_BLUETOOTH_SCAN
                )
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_SCAN)),
                    PERMISSION_REQUEST_BLUETOOTH_SCAN
                )
            }

        }
    }

    private fun cekAdminBTPermission() {
        mLayout= findViewById(R.id.rootLayout)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_ADMIN)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin akses Admin Bluetooth", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses Admin Bluetooth",Snackbar.LENGTH_LONG).show()
            requestAdmintBTPermission()
        }
    }

    private fun requestAdmintBTPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.BLUETOOTH_ADMIN

            )){
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_ADMIN)),
                PERMISSION_REQUEST_BLUETOOTH_ADMIN
            )
        }else{    ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_ADMIN)),
            PERMISSION_REQUEST_BLUETOOTH_ADMIN
        )

        }
    }

    private fun cekBtPermission() {
        mLayout= findViewById(R.id.rootLayout)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin akses Bluetooth only", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses Bluetooh Only",Snackbar.LENGTH_LONG).show()
            requestBluetoothPermission()
        }
    }

    private fun requestBluetoothPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.BLUETOOTH

            )){
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH)),
                PERMISSION_REQUEST_BLUETOOTH
            )
        }else{    ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH)),
            PERMISSION_REQUEST_BLUETOOTH
        )

        }
    }

    private fun cekForPermision() {
        mLayout = findViewById(R.id.rootLayout)
        //if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH)
        //if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin Bluetooth Connect", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses",Snackbar.LENGTH_LONG).show()
            requestBluetoothAdminPermission()
        }
    }

    private fun requestBluetoothAdminPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.BLUETOOTH_CONNECT

            )){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_CONNECT)),
                    PERMISSION_REQUEST_BLUETOOTH_CONNECT
                )
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf((Manifest.permission.BLUETOOTH_CONNECT)),
                    PERMISSION_REQUEST_BLUETOOTH_CONNECT
                )
            }

        }
    }
    companion object {
        private const val PERMISSION_REQUEST_BLUETOOTH_SCAN = 30
        private const val PERMISSION_REQUEST_BLUETOOTH_ADMIN = 0
        private const val PERMISSION_REQUEST_BLUETOOTH_CONNECT = 20
        private const val PERMISSION_REQUEST_BLUETOOTH = 10
    }
}