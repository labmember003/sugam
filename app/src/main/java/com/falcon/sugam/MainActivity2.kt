package com.falcon.sugam

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.sugam.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val language = intent.getStringExtra("message")
        Toast.makeText(this, language, Toast.LENGTH_SHORT).show()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonSummarize.setOnClickListener {
            val intent = Intent(this, SummarizeActivity::class.java)
            intent.putExtra("message", language)
            startActivity(intent)
        }
        binding.buttonComify.setOnClickListener {
            val intent = Intent(this, CommifyActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}