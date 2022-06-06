package com.example.synchronoustechnologytest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    @SerializedName("speed")
    val speed: Float,
    @SerializedName("deg")
    val deg: Int
): Parcelable