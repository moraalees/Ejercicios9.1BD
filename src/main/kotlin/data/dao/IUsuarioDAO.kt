package es.prog2425.ejerciciosBD9_1.data.dao

interface IUsuarioDAO {
    fun insertarCampo(nombre: String, email: String)
}