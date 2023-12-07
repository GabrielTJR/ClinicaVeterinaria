package com.example.clinicaveterinaria

import android.os.Parcelable
import com.google.android.material.textfield.TextInputEditText
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsuarioKotlin(
    val cadastro: Int,
    val nome:String,
    val email:String,
    val senha:String,
    val telefone:String,
    val codigo: String
):Parcelable