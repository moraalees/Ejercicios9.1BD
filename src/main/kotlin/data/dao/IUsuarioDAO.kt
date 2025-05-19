package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Usuario

/**
 * Interfaz que dicta todos los métodos del DAO de [Usuario]
 */
interface IUsuarioDAO {
    fun insertarCampo(nombre: String, email: String)
    fun getAll(): List<Usuario>
    fun getById(id: Int): Usuario?
    fun updateUsuario(nombre: String, id: Int): Boolean
    fun deleteById(id: Int): Boolean
}