package com.example.clinicaveterinaria.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Compras(
    val num: Int,
    val cdg: String,
    val valor: Int,
    val tipo:String,
    val data:String,
    val validade:String
): Parcelable