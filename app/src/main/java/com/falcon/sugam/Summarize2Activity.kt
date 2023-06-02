package com.falcon.sugam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import org.json.JSONObject
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import java.util.*

var url = "http://34.171.182.83/upload"


class Summarize2Activity : AppCompatActivity() {
    lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summarize2)
        findViewById<TextView>(R.id.summarizedText).text = intent.getStringExtra("message")
//        CoroutineScope(Dispatchers.Main).launch {
            val language = intent.getStringExtra("message") ?: "English"
//            val byteArray = intent.getByteArrayExtra("photo")
//            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)!!
//            uploadPhoto(url, bitmap, language)
//
//        }
        findViewById<ImageView>(R.id.speakerButton).setOnClickListener {
            tts = TextToSpeech(this.applicationContext, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS) {
                    tts.language = Locale.US
                    tts.setSpeechRate(1.0f)
                    tts.speak(findViewById<TextView>(R.id.summarizedText).text.toString(), TextToSpeech.QUEUE_ADD, null)
                }
            })
        }
        findViewById<TextView>(R.id.summarizedText).text
        getJson(findViewById<TextView>(R.id.summarizedText).text.toString())
    }
    private fun getJson(text: String) {
        url = "$url?text=$text"
        Log.i("cattttt", url)
//        Toast.makeText(this, "CAT", Toast.LENGTH_SHORT).show()
        Fuel.get(url)
            .responseString { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        val responseBody = result.get()
                        val resultJson = JSONObject(responseBody)
                        val textValue = resultJson.getString("text")
                        val idValue = resultJson.getString("id")
                        Log.i("happyhappy", textValue)
                        Log.i("happyhappyid", idValue)
                        findViewById<TextView>(R.id.testview).text = textValue
                        findViewById<TextView>(R.id.summarizedText).text = textValue
                    }
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("sexyyyy", error.message.toString())
                        // Handle the error case
                        println("Request failed: ${error.message}")
                    }
                }
            }
    }
}