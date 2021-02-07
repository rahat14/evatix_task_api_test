package com.spinnertech.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {

        val navigationController = findNavController(R.id.nav_host_fragment)
        Log.d("TAG", "onBackPressed: "  + navigationController.currentDestination?.id +  " s " + R.id.loginFragment)
         when(navigationController.currentDestination?.id){


             R.id.loginFragment->{
                 triggerDialoguer()
             }
             R.id.dashboardFragment->{
                 triggerDialoguer()
             }
             else ->{
               super.onBackPressed()
             }


         }


    }

    private fun triggerDialoguer() {
        //var dialog : AlertDialog =
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton(
            "OK"
        ) { dialog, _ ->
            dialog.cancel()
           moveTaskToBack(true)
            exitProcess(-1)
        }
        alertDialogBuilder.setNegativeButton("no", null)
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }


}





