package com.gamecodeschool.mylogicofpermission

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var bAdapter :BluetoothAdapter
    //lateinit var lAdapter: BluetoothAdapter
    private val REQUEST_CODE_ENABLE_BT:Int =1
    lateinit var mLayout :View
    private lateinit var m_pairedDevices: Set<BluetoothDevice>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnBluetooth = findViewById<Button>(R.id.btnBluetooth)
        val btnConnPermit = findViewById<Button>(R.id.btnConnPermit)
        val btnAdminPermit =  findViewById<Button>(R.id.btnBtAdminPermit)
        val btnBtScanPermit = findViewById<Button>(R.id.btnBtScanPermit)
        val turnOnBtn= findViewById<Button>(R.id.turnOnBtn)
        val cekBtExist= findViewById<Button>(R.id.cekBtExist)
        val turnOffBT=findViewById<Button>(R.id.turnOffBT)
        val select_device_refresh=findViewById<Button>(R.id.select_device_refresh)
        val discoverBT = findViewById<Button>(R.id.discoverBT)

        //init bluetooth

        bAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        bluetoothManager.getAdapter()
        cekBtExist.setOnClickListener{//isBtExist()
            testing()}
        btnBluetooth.setOnClickListener { cekBtPermission() }
        btnConnPermit.setOnClickListener { cekForBTConPermision() }
        btnAdminPermit.setOnClickListener { cekAdminBTPermission() }
        btnBtScanPermit.setOnClickListener { cekScanBTPermission() }
        turnOnBtn.setOnClickListener {hidupkanBT()}
        turnOffBT.setOnClickListener{matikanBT()}
        discoverBT.setOnClickListener{cariBT()}

        select_device_refresh.setOnClickListener{ cobapairing() }

        //val bluetoothManager=applicationContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

    }

    private fun cariBT() {

        // Create a BroadcastReceiver for ACTION_FOUND.
        //private val receiver = object : BroadcastReceiver() {
        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val action: String? = intent.action
                when(action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Discovery has found a device. Get the BluetoothDevice
                        // object and its info from the Intent.
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName = device?.name
                        val deviceHardwareAddress = device?.address // MAC address
                    }
                }
            }
        }
        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }

    private fun cobapairing() {
        //var pairedDevices=bAdapter.bondedDevices
        var pairedDevices:Set<BluetoothDevice>?=bAdapter?.bondedDevices
        var data:StringBuffer=StringBuffer()
        //for (device:BluetoothDevice in pairedDevices)
        if (pairedDevices != null) {
            for (device:BluetoothDevice in pairedDevices)
            {
                data.append("Device Name="+device.name+"Device Address="+device.address)
                    //device name adalah nama devicenya  --- device address adalaha MAC Addressnya
            }
        }
        if(data.isEmpty()){
            Toast.makeText(this,"Bluetooth Devices is not paired  ",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,data,Toast.LENGTH_SHORT).show()
        }
    }

    private fun pairedDeviceList() {
        //m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        m_pairedDevices = bAdapter.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device)
            }
        } else {
            Toast.makeText(this,"no paired bluetooth devices found", Toast.LENGTH_LONG).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        val select_device_list = findViewById<ListView>(R.id.list_item)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address

            //val intent = Intent(this, ControlActivity::class.java)
            //intent.putExtra(EXTRA_ADDRESS, address)
            //startActivity(intent)
        }
    }

    private fun matikanBT() {

            bAdapter.disable()
            Toast.makeText(this,"Turn Off Bluetooth", Toast.LENGTH_LONG).show()
    }


    private fun hidupkanBT() {
        if (bAdapter.isEnabled){
            Toast.makeText(this,"already on", Toast.LENGTH_LONG).show()
        }
        else{
            //cekForPermision()
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent,REQUEST_CODE_ENABLE_BT)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isBtExist() {
        mLayout= findViewById(R.id.rootLayout)
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Snackbar.make(mLayout,"sepertinya hp nya tidak punya Bluetooth",Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Hp keren punya Bluetooth",Snackbar.LENGTH_LONG).show()
        }
    }

    private fun cekScanBTPermission() {
        mLayout= findViewById(R.id.rootLayout)
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_SCAN)
            ==PackageManager.PERMISSION_GRANTED){
            Snackbar.make(mLayout,"Sudah diberikan izin akses Scan Bluetooth", Snackbar.LENGTH_LONG).show()
        }
        else{
            Snackbar.make(mLayout,"Belum diberikan izin akses Scan Bluetooth",Snackbar.LENGTH_LONG).show()
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

private fun testing(){
    //int result = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    //return result == PackageManager.PERMISSION_GRANTED

    val hasilCheckBtCon = checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_CONNECT)
    val hasilCheckBt = checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH)
    val hasilCheckBtScan = checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_SCAN)
    val hasilCheckBtAdmin= checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)

    val statusIzinOk = PackageManager.PERMISSION_GRANTED
    val statusIzinNo = PackageManager.PERMISSION_DENIED

    //println("uraaa "+statusIzinOk)
    //println("uraaa "+statusIzinNo)

    println("BtConn "+hasilCheckBtCon)
    println("Bt doang "+hasilCheckBt)
    println("Bt Scan "+hasilCheckBtScan)
    println("Bt Admin "+hasilCheckBtAdmin)

}



//    private fun cekForBTConPermision() {
//        mLayout = findViewById(R.id.rootLayout)
//        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)
//            ==PackageManager.PERMISSION_GRANTED){
//            Snackbar.make(mLayout,"Sudah diberikan izin Bluetooth Connect", Snackbar.LENGTH_LONG).show()
//        }
//        else{
//            Snackbar.make(mLayout,"Belum diberikan izin akses",Snackbar.LENGTH_LONG).show()
//            requestBluetoothAdminPermission()
//        }
//    }
private fun cekForBTConPermision() {
    mLayout = findViewById(R.id.rootLayout)
    // Permission has not been granted and must be requested.
    //if (ActivityCompat.shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.BLUETOOTH_CONNECT)) {
        Snackbar.make(mLayout,"Bluetooth connect di butuhkan",Snackbar.LENGTH_LONG).show()

        }
            else {
        Snackbar.make(mLayout,"Bluetooth Printer tidak dapat digunakan",Snackbar.LENGTH_LONG).show()
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
        val EXTRA_ADDRESS: String = "Device_address"
    }


}