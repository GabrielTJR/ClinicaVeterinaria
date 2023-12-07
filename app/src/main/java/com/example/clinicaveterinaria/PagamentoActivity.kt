package com.example.clinicaveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import com.example.clinicaveterinaria.database.ComprasDAO
import com.example.clinicaveterinaria.database.UserDAO
import com.example.clinicaveterinaria.databinding.ActivityLoginBinding
import com.example.clinicaveterinaria.databinding.ActivityPagamentoBinding
import com.example.clinicaveterinaria.model.Compras
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private lateinit var binding: ActivityPagamentoBinding

class PagamentoActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val radioButtonCredito = findViewById<RadioButton>(R.id.pag_btn_credito)
        val radioButtonDebito = findViewById<RadioButton>(R.id.pag_btn_debito)
        val radioButtonPix = findViewById<RadioButton>(R.id.pag_btn_pix)

        radioButtonCredito.setOnClickListener {
            radioButtonCredito.isChecked = true
            radioButtonDebito.isChecked = false
            radioButtonPix.isChecked = false
        }

        radioButtonDebito.setOnClickListener {
            radioButtonDebito.isChecked = true
            radioButtonCredito.isChecked = false
            radioButtonPix.isChecked = false
        }

        radioButtonPix.setOnClickListener {
            radioButtonPix.isChecked = true
            radioButtonCredito.isChecked = false
            radioButtonDebito.isChecked = false
        }


        binding.pagBtnPagar.setOnClickListener {
            if (radioButtonCredito.isChecked || radioButtonDebito.isChecked || radioButtonPix.isChecked) {
                val intent = Intent(this, MenuActivity::class.java)
                val comprasDAO = ComprasDAO(this)
                val compras:Compras
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val dataAtual = Date() // Obtém a data atual
                val calendar = Calendar.getInstance()
                calendar.time = dataAtual
                val dataCompra = dateFormat.format(Date()) // Obtém a data atual
                val codigoRecebido = LoginActivity.codigoGlobal
                Log.i("codigo", codigoRecebido)

                if (Mensal == true) {
                    calendar.add(Calendar.MONTH, 1)
                    val validade = dateFormat.format(calendar.time)
                    compras = Compras(
                        -1, codigoRecebido,70, "Mensal", dataCompra, validade
                    )
                } else {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    val validade = dateFormat.format(calendar.time)
                    compras = Compras(
                        -1, codigoRecebido,10, "Diário", dataCompra, validade
                    )
                }
                if(comprasDAO.salvar(compras)){
                    Log.i("database", "Compra salva com sucesso.")
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                }else {
                    Log.i("database", "Compra não foi salva.")
                }
                startActivity(intent)
            } else {
                showToast("Por favor, selecione uma opção antes de confirmar.")
            }
        }

        binding.pagBtnSair.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            Mensal = false
            Diario = false
            startActivity(intent)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}