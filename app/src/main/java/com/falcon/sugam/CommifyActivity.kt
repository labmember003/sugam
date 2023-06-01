package com.falcon.sugam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.falcon.sugam.databinding.ActivityCommifyBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import org.json.JSONObject

private val url2 = "http://34.171.182.83/comikify"

class CommifyActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCommifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitButton.setOnClickListener {
            getJson(binding.yourNoteET.text.toString())
        }
    }

    private fun getJson(text: String) {
        Toast.makeText(this, "SEX", Toast.LENGTH_SHORT).show()
        Fuel.post(url2)
            .header("Content-Type" to "application/json")

            .response { result ->
                // Handle the response
                when (result) {
                    is Result.Success -> {
                        val data = result.value
                        val jsonString = String(data)
                        val jsonObject = JSONObject(jsonString)
                        Toast.makeText(this, jsonObject.toString(), Toast.LENGTH_SHORT).show()
                        // Process the JSON response
                        // Here you can parse the jsonString and access the "text" and "id" values
                    }
                    is Result.Failure -> {
                        val error = result.error
                        Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
                        // Handle the error
                    }
                }
            }
    }
}