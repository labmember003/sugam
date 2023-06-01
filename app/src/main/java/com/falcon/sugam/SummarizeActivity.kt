package com.falcon.sugam

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.falcon.sugam.databinding.ActivityMainBinding
import com.falcon.sugam.databinding.ActivitySummarizeBinding
import java.io.FileOutputStream

private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivitySummarizeBinding



class SummarizeActivity : AppCompatActivity() {

    companion object {
        // Define the pic id
        private const val pic_id = 123
    }
    var language: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val message = intent.getStringExtra("message")
        language = intent.getStringExtra("message") ?: ""
        binding = ActivitySummarizeBinding.inflate(layoutInflater)
        binding.scanButton.setOnClickListener {
            val camera_intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            } else {
                TODO("VERSION.SDK_INT < CUPCAKE")
            }
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, pic_id)
        }

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            val photo = data!!.extras!!["data"] as Bitmap?
            Toast.makeText(this, photo.toString(), Toast.LENGTH_SHORT).show()
            uploadPhoto(photo)
        }


    }

    private fun uploadPhoto(photo: Bitmap?) {
        val message = intent.getStringExtra("message")
        language
        Toast.makeText(this, "language", Toast.LENGTH_SHORT).show()
    }
}