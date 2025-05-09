package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.model.Usuario

class UsuarioService(private val dao: UsuarioDAOH2) : IUsuarioService {
    override fun addUsuario(nombre: String, correo: String) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(correo.isNotBlank()){ "El correo no puede estar vacío." }
        dao.insertarCampo(nombre.trim(), correo.trim())
    }

    override fun addUsuario(usuario: Usuario) {
        require(usuario.nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(usuario.correo.isNotBlank()){ "El correo no puede estar vacío." }
        dao.insertarCampo(usuario.nombre.trim(), usuario.correo.trim())
    }


}