package com.example.clinicaveterinaria.database

import com.example.clinicaveterinaria.UsuarioKotlin

interface IUserDAO {
    fun salvar( usuarioKotlin: UsuarioKotlin): Boolean
    fun atualizar( usuarioKotlin: UsuarioKotlin): Boolean
    fun deletar( usuarioKotlin: UsuarioKotlin): Boolean
    fun listar( usuarioKotlin: UsuarioKotlin): List<UsuarioKotlin>
    fun buscarUsuarioPorCodigo(codigo: String): UsuarioKotlin?
    fun codigoUsuarioJaExiste(codigo: String): Boolean
}