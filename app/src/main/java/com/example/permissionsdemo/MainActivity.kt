package com.example.permissionsdemo


import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val cameraLocation : ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){isGranted ->
        isGranted.entries.forEach{
            val permissionname = it.key
            val permissionid = it.value

            if (permissionid){
                Toast.makeText(this,"Permission Granted for Camera and Location",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Permission Denied for Camera and Location",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.secondbtn)

        btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA
            )){
                showRationalDialog("Permission Demo requires Camera Access",
                "Camera cannot be used because Camera Access is denied")
            }
            else{
                cameraLocation.launch(
                    arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }
    }
    private fun showRationalDialog(
        title : String,
        message : String,
    ){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setPositiveButton("Cancel"){
                dialog,_ ->
                dialog.dismiss()
            }
            builder.create().show()
    }
}
