package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Usuario

interface IUsuarioDAO {
    fun insertarCampo(nombre: String, email: String)
    fun insertarCampo(usuario: Usuario)
}