package com.example.clinicaveterinaria.database

import com.example.clinicaveterinaria.UsuarioKotlin
import com.example.clinicaveterinaria.model.Compras

interface IComprasDAO {
    fun salvar( compras: Compras): Boolean
    fun atualizar( compras: Compras): Boolean
    fun deletar( compras: Compras): Boolean
    fun listar(): List<Compras>
    fun listarPorCdg(codigo: String): List<Compras>
    fun obterDataValidadePorCdg(codigo: String): String?
    fun obterPlanoPorCdg(codigo: String): String?
}