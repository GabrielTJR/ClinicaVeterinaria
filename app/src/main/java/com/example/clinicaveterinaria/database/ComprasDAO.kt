package com.example.clinicaveterinaria.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.clinicaveterinaria.model.Compras

class ComprasDAO(context: Context) : IComprasDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(compras: Compras): Boolean {
        val valores = ContentValues()
        valores.put("${DatabaseHelper.TCOMPRAS_VALOR}", compras.valor)
        valores.put("${DatabaseHelper.TCOMPRAS_CDG}", compras.cdg)
        valores.put("${DatabaseHelper.TCOMPRAS_TIPO}", compras.tipo)
        valores.put("${DatabaseHelper.TCOMPRAS_DATA}", compras.data)
        valores.put("${DatabaseHelper.TCOMPRAS_VALIDADE}", compras.validade)
        try{
            escrita.insert(
                DatabaseHelper.TABELA_COMPRAS, null, valores
            )
            Log.i("database","Compra inserida na tabela. ${DatabaseHelper.TABELA_COMPRAS}.")
        }catch (e:Exception){
            Log.i("database","Erro ao inserir compra na tabela. ${DatabaseHelper.TABELA_COMPRAS}.")
            return false
        }
        return true
    }

    override fun atualizar(compras: Compras): Boolean {
        val args = arrayOf(compras.num.toString())
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.TCOMPRAS_TIPO}",compras.tipo)
        conteudo.put("${DatabaseHelper.TCOMPRAS_CDG}",compras.cdg)
        conteudo.put("${DatabaseHelper.TCOMPRAS_VALOR}",compras.valor)
        conteudo.put("${DatabaseHelper.TCOMPRAS_DATA}", compras.data)
        conteudo.put("${DatabaseHelper.TCOMPRAS_VALIDADE}", compras.validade)
        try {
            escrita.update(
                DatabaseHelper.TABELA_COMPRAS,
                conteudo,
                "${DatabaseHelper.TCOMPRAS_NUM} = ?",
                args
            )

        } catch (e: Exception) {
            Log.i("database","Não foi possível atualizar compra.")
            return false
        }
        Log.i("database","Compra atualizada com sucesso.")
        return true
    }

    override fun deletar(compras: Compras): Boolean {
        try {
            // Deleta todos os registros da tabela
            escrita.delete(DatabaseHelper.TABELA_COMPRAS, null, null)
            Log.i("database", "Todos os registros foram deletados da tabela ${DatabaseHelper.TABELA_COMPRAS}.")
        } catch (e: Exception) {
            Log.e("database", "Erro ao deletar registros da tabela ${DatabaseHelper.TABELA_COMPRAS}.", e)
            return false
        }
        return true
    }

    override fun listar(): List<Compras> {
        val listaCompras = ArrayList<Compras>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_COMPRAS} ORDER BY ${DatabaseHelper.TCOMPRAS_NUM}"
        val cursor = leitura.rawQuery(sql, null)
        //capturando os indices das colunas
        val iNum = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_NUM);
        val iCdg = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_CDG);
        val iValor = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_VALOR);
        val iTipo = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_TIPO);
        val iData = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_DATA);
        val iValidade = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_VALIDADE);

        while (cursor.moveToNext()) {
            val num = cursor.getInt(iNum)
            val cdg = cursor.getString(iCdg)
            val valor = cursor.getInt(iValor)
            val tipo = cursor.getString(iTipo)
            val data = cursor.getString(iData)
            val validade = cursor.getString(iValidade)
            listaCompras.add(
                Compras(num, cdg, valor, tipo, data, validade)
            )
            Log.i("compras", "Compra adicionada a lista. \n")
        }
        return listaCompras
    }

    override fun listarPorCdg(codigo: String): List<Compras> {
        val listaCompras = ArrayList<Compras>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_COMPRAS} WHERE ${DatabaseHelper.TCOMPRAS_CDG} = ?"
        val cursor = leitura.rawQuery(sql, arrayOf(codigo))

        //capturando os índices das colunas
        val iNum = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_NUM)
        val iCdg = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_CDG)
        val iValor = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_VALOR)
        val iTipo = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_TIPO)
        val iData = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_DATA)
        val iValidade = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_VALIDADE)

        while (cursor.moveToNext()) {
            val num = cursor.getInt(iNum)
            val cdg = cursor.getString(iCdg)
            val valor = cursor.getInt(iValor)
            val tipo = cursor.getString(iTipo)
            val data = cursor.getString(iData)
            val validade = cursor.getString(iValidade)

            listaCompras.add(
                Compras(num, cdg, valor, tipo, data, validade)
            )
            Log.i("compras", "Compra adicionada à lista. \n")
        }
        cursor.close()
        return listaCompras
    }

    override fun obterDataValidadePorCdg(codigo: String): String? {
        val sql = "SELECT ${DatabaseHelper.TCOMPRAS_VALIDADE} FROM ${DatabaseHelper.TABELA_COMPRAS} WHERE ${DatabaseHelper.TCOMPRAS_CDG} = ?"
        val cursor = leitura.rawQuery(sql, arrayOf(codigo))

        var dataValidade: String? = null

        if (cursor.moveToNext()) {
            val iValidade = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_VALIDADE)
            dataValidade = cursor.getString(iValidade)
        }
        cursor.close()
        return dataValidade
    }

    override fun obterPlanoPorCdg(codigo: String): String? {
        val sql = "SELECT ${DatabaseHelper.TCOMPRAS_TIPO} FROM ${DatabaseHelper.TABELA_COMPRAS} WHERE ${DatabaseHelper.TCOMPRAS_CDG} = ?"
        val cursor = leitura.rawQuery(sql, arrayOf(codigo))

        var plano: String? = null

        if (cursor.moveToNext()) {
            val iPlano = cursor.getColumnIndex(DatabaseHelper.TCOMPRAS_TIPO)
            plano = cursor.getString(iPlano)
        }

        cursor.close()
        return plano
    }
}