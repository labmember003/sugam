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
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.falcon.sugam.databinding.ActivitySummarize2Binding
import java.util.*

var url = "http://34.171.182.83/upload"
var url3 = "http://34.171.182.83/follow_up"
var globalidValue = 0
class Summarize2Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySummarize2Binding
    lateinit var tts: TextToSpeech
    var language : String = ""

    private val RQ_SPEECH_RC = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivitySummarize2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<TextView>(R.id.summarizedText).text = intent.getStringExtra("message")
        language = intent.getStringExtra("language") ?: "English"

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
        binding.btnSend.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val customView = inflater.inflate(R.layout.message_item, null)
            customView.findViewById<TextView>(R.id.tv_message).text = binding.etMessage.text
            customView.findViewById<TextView>(R.id.tv_bot_message).visibility = View.GONE
            binding.llhehe.addView(customView)
            sendFollowUpRequest(binding.etMessage.text.toString())
        }
    }

    private fun sendFollowUpRequest(text: String) {
        val id: Int = globalidValue
        url3 = "http://34.171.182.83/follow_up?id=$id&text=$text&lang=$language"
        Fuel.post(url3)
            .responseString { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        val responseBody = result.get()
                        val resultJson = JSONObject(responseBody)
                        val textValue = resultJson.getString("followup")
                        Log.i("meribilli", textValue)
                        Handler(Looper.getMainLooper()).post {
                            sendBotMessage(textValue)
                        }
                    }
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("meribilli", error.message.toString())
                        // Handle the error case
                        println("Request failed: ${error.message}")
                    }
                }
            }
    }

    private fun sendBotMessage(textValue: String) {
        val inflater = LayoutInflater.from(this)
        val customView = inflater.inflate(R.layout.message_item, null)
        customView.findViewById<TextView>(R.id.tv_bot_message).text = textValue
        customView.findViewById<TextView>(R.id.tv_message).visibility = View.GONE
        binding.llhehe.addView(customView)
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
        url = "http://34.171.182.83/upload?text=$text&lang=$language"
        Log.i("cattttt", url)
        Fuel.get(url)
            .responseString { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        val responseBody = result.get()
                        val resultJson = JSONObject(responseBody)
                        val textValue = resultJson.getString("text")
                        val idValue = resultJson.getInt("id")
                        globalidValue = idValue
                        Log.i("happyhappy", textValue)
                        Log.i("happyhappyid", idValue.toString())
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