package com.example.clinicaveterinaria

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.clinicaveterinaria.model.UsuarioJava

lateinit var tuserTxtvUsuario: TextView
lateinit var tuserBtnSair:Button

class TelaUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_usuario)

        tuserTxtvUsuario = findViewById(R.id.scuser_txtv_usuario)
        tuserBtnSair = findViewById(R.id.tuser_btn_sair)

        var bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= 33) {
                // metodo disponível a partir da versão 33
                val usuario = bundle.getParcelable("user1", UsuarioKotlin::class.java)
                tuserTxtvUsuario.setText(usuario?.toString())
            } else {
                // metodo utilizado antes da versão 33
                val usuario = bundle.getParcelable<UsuarioKotlin>("user1")
                tuserTxtvUsuario.setText(usuario?.toString())
            }
        }

        tuserBtnSair.setOnClickListener {
            finish()
        }
    }
}


