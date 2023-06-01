package com.falcon.sugam.api

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UserRequest(
    val image: Bitmap,
    val lang: String
) : Serializable
