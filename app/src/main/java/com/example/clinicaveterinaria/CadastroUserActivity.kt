package com.example.clinicaveterinaria

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.clinicaveterinaria.database.UserDAO
import com.example.clinicaveterinaria.databinding.ActivityCadastroUserBinding

private lateinit var binding:ActivityCadastroUserBinding

class CadastroUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.caduBtnCadastrar.setOnClickListener {
            val userDAO = UserDAO(this)
            val user = UsuarioKotlin(
                -1,
                binding.caduItxtNome.text.toString(),
                binding.caduItxtEmail.text.toString(),
                binding.caduItxtSenha.text.toString(),
                binding.caduItxtFone.text.toString(),
                binding.caduItxtCdgAluno.text.toString(),
            )
            if(userDAO.salvar(user)){
                Log.i("database", "Usuário ${user.nome} salvo com sucesso.")
                LoginActivity.codigoGlobal = binding.caduItxtCdgAluno.text.toString()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                //finish()
            }else {
                val mensagemErro = "Este código já tem uma conta criada."
                Log.i("database", "Usuário ${user.nome} não foi salvo.")
                CexibirPopUpErro(this, mensagemErro)
            }
        }
        binding.caduBtnCancelar.setOnClickListener {
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.caduItxtNome.setText("")
        binding.caduItxtEmail.setText("")
        binding.caduItxtSenha.setText("")
        binding.caduItxtFone.setText("")
    }

    private fun CexibirPopUpErro(context: Context, mensagem: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Erro")
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}