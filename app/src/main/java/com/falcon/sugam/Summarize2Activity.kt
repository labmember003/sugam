package com.falcon.sugam

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import org.json.JSONObject
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.falcon.sugam.databinding.ActivitySummarize2Binding
import com.falcon.sugam.databinding.ActivitySummarizeBinding
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

var url = "http://34.171.182.83/upload"


class Summarize2Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySummarize2Binding
    lateinit var tts: TextToSpeech

    private val RQ_SPEECH_RC = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivitySummarize2Binding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.micButton.setOnClickListener {
            askSpeechInput()
        }
    }

    private fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "bk madarchod, deice he chutiya hai tera", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING BRUH")
            startActivityForResult(i, RQ_SPEECH_RC)
        }
    }

    private fun getJson(text: String) {
        url = "$url?text=$text"
        Log.i("cattttt", url)
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
                        Handler(Looper.getMainLooper()).post {
                            binding.summarizedText.text = textValue
                        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_SPEECH_RC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            Toast.makeText(this, result?.get(0).toString(), Toast.LENGTH_SHORT).show()
        }
    }
}