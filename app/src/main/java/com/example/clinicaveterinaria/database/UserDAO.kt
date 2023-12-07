package com.example.clinicaveterinaria.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.clinicaveterinaria.UsuarioKotlin

class UserDAO(context: Context) : IUserDAO{
    private val dbHelper = DatabaseHelper(context)
    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(usuarioKotlin: UsuarioKotlin): Boolean {
        if (codigoUsuarioJaExiste(usuarioKotlin.codigo)) {
            Log.i("database", "Código de usuário já existe. Não foi possível salvar o usuário.")
            return false
        }
        val valores = ContentValues()
        valores.put("${DatabaseHelper.TUSER_NOME}", usuarioKotlin.nome)
        valores.put("${DatabaseHelper.TUSER_EMAIL}", usuarioKotlin.email)
        valores.put("${DatabaseHelper.TUSER_SENHA}", usuarioKotlin.senha)
        valores.put("${DatabaseHelper.TUSER_CODIGO}", usuarioKotlin.codigo)
        valores.put("${DatabaseHelper.TUSER_TELEFONE}", usuarioKotlin.telefone)

        try {
            escrita.insert(
                DatabaseHelper.TABELA_USUARIO, null, valores
            )
            Log.i("database", "Usuário inserido na tabela. ${DatabaseHelper.TABELA_USUARIO}.")
        } catch (e: Exception) {
            Log.i("database", "Erro ao inserir usuário na tabela. ${DatabaseHelper.TABELA_USUARIO}.")
            return false
        }
        return true
    }

    override fun atualizar(usuarioKotlin: UsuarioKotlin): Boolean {
        try{

        }catch (e:Exception){
            return false
        }
        return true
    }

    override fun deletar(usuarioKotlin: UsuarioKotlin): Boolean {
        try{

        }catch (e:Exception){
            return false
        }
        return true
    }

    override fun listar(usuarioKotlin: UsuarioKotlin): List<UsuarioKotlin> {
        val listUsers = ArrayList<UsuarioKotlin>()
        return listUsers
    }

    fun obterUsuarios(): List<UsuarioKotlin> {
        val usuarios = mutableListOf<UsuarioKotlin>()
        val db = dbHelper.getReadableDatabase()

        val cursor = db.query(
            DatabaseHelper.TABELA_USUARIO,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use { cursor ->
            val idIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_CADASTRO)
            val nomeIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_NOME)
            val emailIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_EMAIL)
            val senhaIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_SENHA)
            val telefoneIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_TELEFONE)
            val codigoIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_CODIGO)

            while (cursor.moveToNext()) {
                if (idIndex != -1 && nomeIndex != -1 && emailIndex != -1 && senhaIndex != -1 && telefoneIndex != -1 && codigoIndex != -1) {
                    val id = cursor.getInt(idIndex)
                    val nome = cursor.getString(nomeIndex)
                    val email = cursor.getString(emailIndex)
                    val senha = cursor.getString(senhaIndex)
                    val telefone = cursor.getString(telefoneIndex)
                    val codigo = cursor.getString(codigoIndex)

                    val usuario = UsuarioKotlin(id, nome, email, senha, telefone, codigo)
                    usuarios.add(usuario)
                } else {
                    // Lida com o caso em que pelo menos um índice não é encontrado
                    Log.e("database", "Pelo menos um índice de coluna não encontrado.")
                }
            }
        }

        db.close()
        return usuarios
    }

    override fun buscarUsuarioPorCodigo(codigo: String): UsuarioKotlin? {
        val query = "SELECT * FROM ${DatabaseHelper.TABELA_USUARIO} WHERE ${DatabaseHelper.TUSER_CODIGO} = ?"
        val selectionArgs = arrayOf(codigo)

        val cursor = leitura.rawQuery(query, selectionArgs)

        return try {
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_CADASTRO)
                val nomeIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_NOME)
                val emailIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_EMAIL)
                val senhaIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_SENHA)
                val telefoneIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_TELEFONE)
                val codigoIndex = cursor.getColumnIndex(DatabaseHelper.TUSER_CODIGO)

                val id = cursor.getInt(idIndex)
                val nome = cursor.getString(nomeIndex)
                val email = cursor.getString(emailIndex)
                val senha = cursor.getString(senhaIndex)
                val telefone = cursor.getString(telefoneIndex)
                val codigoUsuario = cursor.getString(codigoIndex)

                UsuarioKotlin(id, nome, email, senha, telefone, codigoUsuario)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("database", "Erro ao buscar usuário por código: ${e.message}")
            null
        } finally {
            cursor?.close()
        }
    }

    override fun codigoUsuarioJaExiste(codigo: String): Boolean {
        val query = "SELECT COUNT(*) FROM ${DatabaseHelper.TABELA_USUARIO} WHERE ${DatabaseHelper.TUSER_CODIGO} = ?"
        val selectionArgs = arrayOf(codigo)
        val cursor = leitura.rawQuery(query, selectionArgs)

        return try {
            if (cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                count > 0
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("database", "Erro ao verificar a existência do código de usuário: ${e.message}")
            false
        } finally {
            cursor?.close()
        }
    }


}
