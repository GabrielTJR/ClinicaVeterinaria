package com.example.clinicaveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

lateinit var tiniBtnSair:Button
lateinit var tiniTxtEmail:TextView
lateinit var tiniTxtSenha:TextView

class TelaInicialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)
        tiniBtnSair = findViewById(R.id.tini_btn_sair)
        tiniTxtEmail = findViewById(R.id.tini_txtv_email)
        tiniTxtSenha = findViewById(R.id.tini_txtv_senha)

        tiniBtnSair.setOnClickListener {
            finish()
        }

        var bundle = intent.extras
        if (bundle != null){
            tiniTxtEmail.setText(bundle.getString("email"))
            tiniTxtSenha.setText(bundle.getString("senha"))
        }

    }

}