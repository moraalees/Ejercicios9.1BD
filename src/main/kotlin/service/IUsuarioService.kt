package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Usuario

interface IUsuarioService {
    fun addUsuario(nombre: String, correo: String)
    fun addUsuario(usuario: Usuario)
}