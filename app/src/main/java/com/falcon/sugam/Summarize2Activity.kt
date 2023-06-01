package com.falcon.sugam

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import org.json.JSONObject


val url = "https://example.com/api/endpoint"

class Summarize2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summarize2)
        CoroutineScope(Dispatchers.Main).launch {
            val language = intent.getStringExtra("message") ?: "English"
            val byteArray = intent.getByteArrayExtra("photo")
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)!!
            uploadPhoto(url, bitmap, language)
        }
    }
    private fun uploadPhoto(url: String, bitmap: Bitmap, language: String) {
        Log.i("ohhhhhs", bitmap.toString())
        Log.i("ohhhhhs", language)
        Fuel.post(url)
            .header("Content-Type" to "application/json")
            .body("{ \"image\": \"$bitmap\", \"lang\": \"$language\" }")
            .response { result ->
                // Handle the response
                when (result) {
                    is Result.Success -> {
                        val data = result.value
                        val jsonString = String(data)
                        val jsonObject = JSONObject(jsonString)
                        val text = jsonObject.getString("text")
                        val id = jsonObject.getInt("id")
                        findViewById<TextView>(R.id.summarizedText).text = text
                        // Process the JSON response
                        // Here you can parse the jsonString and access the "text" and "id" values
                    }
                    is Result.Failure -> {
                        val error = result.error
                        findViewById<TextView>(R.id.summarizedText).text = error.message.toString()
                        // Handle the error
                    }
                }
            }
    }
}