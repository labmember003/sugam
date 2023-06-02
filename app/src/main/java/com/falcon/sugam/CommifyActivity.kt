package com.falcon.sugam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.falcon.sugam.databinding.ActivityCommifyBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

private var url2 = "http://34.171.182.83/comikify"

class CommifyActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCommifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitButton.setOnClickListener {
            url2 = url2 + "?topic=" + binding.yourNoteET.text.toString()
            getJson()
        }
    }

    private fun getJson() {
//        Toast.makeText(this, "CAT", Toast.LENGTH_SHORT).show()
        val requestBody = """
        {
            "topic": "cat"
        }
    """.trimIndent()
        Fuel.get(url2)
            .body(requestBody)
            .responseString { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        val responseBody = result.get()
                        val resultJson = JSONObject(responseBody)
                        val resultValue = resultJson.getString("result")
                        val data: List<String> = convertDataToList(resultValue)
                        val arrayListData: ArrayList<String> = ArrayList(data)

                        val intent = Intent(this, Commify2Activity::class.java)
                        intent.putStringArrayListExtra("data", arrayListData)
                        startActivity(intent)

                        Log.i("sexyyyy", data[0])

                        // Handle the result value as needed
                        // For example, print it to the console
                        println("Result: $resultValue")
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
    private fun convertDataToList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, listType)
    }
}