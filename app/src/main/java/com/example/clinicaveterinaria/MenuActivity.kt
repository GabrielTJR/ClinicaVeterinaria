    package com.example.clinicaveterinaria

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clinicaveterinaria.database.ComprasDAO
import com.example.clinicaveterinaria.database.UserDAO
import com.example.clinicaveterinaria.databinding.ActivityMenuBinding
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

    private lateinit var binding: ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var userDAO: UserDAO
    private lateinit var comprasDAO: ComprasDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        comprasDAO = ComprasDAO(this)

        userDAO = UserDAO(this)

        binding.menuBtnServico.setOnClickListener {
            //val usuario = userDAO.buscarUsuarioPorCodigo(LoginActivity.codigoGlobal)
            val dataAgora = Date()
            val dataValidadeString = comprasDAO.obterDataValidadePorCdg(LoginActivity.codigoGlobal)
            if (dataValidadeString != null) {
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val dataValidade = formatter.parse(dataValidadeString)

                if (dataAgora < dataValidade) {
                    val mensagemErro = "Você já tem um plano Válido."
                    Log.i("database", mensagemErro)
                    MexibirPopUpErro(this, mensagemErro)
                } else {
                    val intent = Intent(this, ServicosActivity::class.java)
                    startActivity(intent)
                }
            }else{
                val intent = Intent(this, ServicosActivity::class.java)
                startActivity(intent)
            }

        }


        binding.menuBtnCompras.setOnClickListener {
            val intent = Intent(this, ComprasActivity::class.java)
            startActivity(intent)
        }

        binding.menuBtnSair.setOnClickListener {
            val usuarios: List<UsuarioKotlin> = userDAO.obterUsuarios()

            for (usuario in usuarios) {
                val nome = usuario.nome
                val codigo = usuario.codigo
                val senha = usuario.senha

                Log.i("database", "Usuário: $nome, Codigo: $codigo, Senha: $senha")
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

     private fun MexibirPopUpErro(context: Context, mensagem: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Erro")
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        var TemPlanoValido: Boolean = false
    }
}
