package com.example.clinicaveterinaria.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DatabaseHelper (context : Context): SQLiteOpenHelper(
    context, "estacionamento", null, 1
){
    companion object{
        const val NOME_BANCO = "estacionamento"

        const val TABELA_USUARIO = "UserTable"
        const val TUSER_CADASTRO = "cadastro"
        const val TUSER_NOME = "nome"
        const val TUSER_EMAIL = "email"
        const val TUSER_SENHA = "senha"
        const val TUSER_TELEFONE = "telefone"
        const val TUSER_CODIGO = "codigo"

        const val TABELA_COMPRAS = "ComprasTable"
        const val TCOMPRAS_NUM = "registro"
        const val TCOMPRAS_CDG = "codigo"
        const val TCOMPRAS_TIPO = "compra"
        const val TCOMPRAS_VALOR = "valor"
        const val TCOMPRAS_DATA = "data"
        const val TCOMPRAS_VALIDADE = "validade"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val sqluser = "CREATE TABLE UserTable(" +
                   "cadastro integer PRIMARY KEY AUTOINCREMENT," +
                   "nome varchar(100) NOT NULL," +
                   "email varchar(100) NOT NULL," +
                   "senha varchar(20) NOT NULL," +
                   "telefone integer NOT NULL," +
                   "codigo varchar(20) NOT NULL" +
                ");"
        try{
            db?.execSQL(sqluser)
            Log.i("database", "Sucesso ao criar a tabela")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("database", "Erro ao criar tabela")
        }

        val sqlcompras = "CREATE TABLE ComprasTable(" +
                "registro integer PRIMARY KEY AUTOINCREMENT," +
                "codigo varchar(20) NOT NULL," +
                "compra varchar(20) NOT NULL," +
                "valor integer NOT NULL," +
                "data varchar(20) NOT NULL," +
                "validade varchar(20) NOT NULL" +
                ");"
        try{
            db?.execSQL(sqlcompras)
            Log.i("compras", "Sucesso ao criar a tabela")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("compras", "Erro ao criar tabela")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Adicione mais blocos if para versões futuras, se necessário
    }

    override fun getReadableDatabase(): SQLiteDatabase  {
        return super.getReadableDatabase()
    }

    fun excluirTabela() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $NOME_BANCO")
        db.close()
    }
}