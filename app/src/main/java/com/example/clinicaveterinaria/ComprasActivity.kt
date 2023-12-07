package com.example.clinicaveterinaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.clinicaveterinaria.database.ComprasDAO
import com.example.clinicaveterinaria.databinding.ActivityComprasBinding
import com.example.clinicaveterinaria.fragments.ConsultasFragment

private lateinit var binding:ActivityComprasBinding

class ComprasActivity : AppCompatActivity() {

    private lateinit var comprasDAO: ComprasDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComprasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        comprasDAO = ComprasDAO(this)

        val consultasFragment = ConsultasFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.codigoFragment, consultasFragment)
                .commit()

        binding.comprasBtnVoltar.setOnClickListener {
            finish()
        }
        val meuTextView = findViewById<TextView>(R.id.comprasTextPlano)
        var texto: String = ""
        if(comprasDAO.obterPlanoPorCdg(LoginActivity.codigoGlobal) != null){
            texto = "Possui pagamento " + comprasDAO.obterPlanoPorCdg(LoginActivity.codigoGlobal) + " "
        }else{
            texto = "Não foi obtido nada"
        }
        trocarTextoDoTextView(meuTextView, texto ?: "Não foi obtido nada")


    }
    fun trocarTextoDoTextView(textView: TextView, novoTexto: String){
        textView.text = novoTexto
    }
}