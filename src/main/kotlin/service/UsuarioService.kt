package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2

class UsuarioService(private val dao: UsuarioDAOH2) : IUsuarioService {
    override fun addUsuario(nombre: String, correo: String) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(correo.isNotBlank()){ "El nombre no puede estar vacío." }
        dao.insertarCampo(nombre.trim(), correo.trim())
    }


}