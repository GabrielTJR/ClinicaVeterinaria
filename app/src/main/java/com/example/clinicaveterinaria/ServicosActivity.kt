package com.example.clinicaveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Bundle
import android.widget.RadioButton
import com.example.clinicaveterinaria.databinding.ActivityServicosBinding

private lateinit var binding: ActivityServicosBinding

class ServicosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val radioButtonDiario = findViewById<RadioButton>(R.id.serv_btn_diario)
        val radioButtonMensal = findViewById<RadioButton>(R.id.serv_btn_mensal)

        radioButtonDiario.setOnClickListener {
                radioButtonDiario.isChecked = true
                radioButtonMensal.isChecked = false
        }

        radioButtonMensal.setOnClickListener {
                radioButtonMensal.isChecked = true
                radioButtonDiario.isChecked = false
        }


        binding.servBtnConfirmar.setOnClickListener {
            if (radioButtonDiario.isChecked || radioButtonMensal.isChecked) {
                val intent = Intent(this, PagamentoActivity::class.java)
                if(radioButtonDiario.isChecked){
                    intent.putExtra("preco", binding.servBtnPreco1.text.toString())
                    Diario = true
                    Mensal = false
                }else {
                    intent.putExtra("preco", binding.servBtnPreco2.text.toString())
                    Mensal = true
                    Diario = false
                }
                startActivity(intent)
            } else {
                showToast("Por favor, selecione uma opção antes de confirmar.")
            }
        }

        binding.servBtnSair.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}