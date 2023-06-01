package com.falcon.sugam

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.falcon.sugam.Constants.BASE_URL
import com.falcon.sugam.api.UserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Summarize2Activity : AppCompatActivity() {

    val summarizeAPIService = Api.fileApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summarize2)
        CoroutineScope(Dispatchers.Main).launch {
            val language = intent.getStringExtra("message") ?: "English"
            val byteArray = intent.getByteArrayExtra("photo")
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)!!
            uploadPhoto(bitmap, language)
        }
    }
    private suspend fun uploadPhoto(bitmap: Bitmap, language: String) {
        Log.i("ohhhhhs", bitmap.toString())
        Log.i("ohhhhhs", language)
        val dynamicEndpoint = ""
        val response = summarizeAPIService.getSummarizedText(
            UserRequest(
                bitmap,
                language
            )
        )
        response.body()
    }
}