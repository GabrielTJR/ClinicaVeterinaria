package com.example.clinicaveterinaria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.clinicaveterinaria.databinding.ActivityLoginBinding
import com.example.clinicaveterinaria.database.UserDAO

private lateinit var binding:ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.loguBtnLogar.setOnClickListener {
            val codigo = binding.loguItxtCdgAluno.text.toString()
            val senha = binding.loguItxtSenha.text.toString()

            if (fazerLogin(codigo, senha)) {
                if (codigo != null) {
                    codigoGlobal = codigo
                }
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("login", "Falha no login. Verifique suas credenciais.")
            }
        }
        binding.loguBtnCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.loguItxtCdgAluno.setText("")
        binding.loguItxtSenha.setText("")
    }

    private fun fazerLogin(codigo: String, senha: String): Boolean {
        val userDAO = UserDAO(this) // Substitua pelo nome correto do seu DAO
        val usuario = userDAO.buscarUsuarioPorCodigo(codigo)

        if (usuario != null && usuario.senha == senha) {
            Log.i("login", "Login bem-sucedido para o usuário ${usuario.nome}.")
            return true
        } else {
            val mensagemErro = "Senha Errada."
            Log.i("database", mensagemErro)
            LexibirPopUpErro(this, mensagemErro)
            return false
        }
    }

    private fun LexibirPopUpErro(context: Context, mensagem: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Erro")
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        var codigoGlobal: String = ""
    }
}