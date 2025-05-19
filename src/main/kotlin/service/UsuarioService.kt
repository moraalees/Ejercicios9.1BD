package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.model.Usuario

class UsuarioService(private val dao: UsuarioDAOH2) : IUsuarioService {
    /**
     * Añade un nuevo usuario utilizando valores individuales.
     *
     * @param nombre Nombre del usuario.
     * @param correo Dirección de correo electrónico del usuario.
     *
     * Valida que el nombre y el correo no estén vacíos antes de llamar al metodo del DAO correspondiente.
     * Llama al DAO para insertar el nuevo usuario en la base de datos.
     */
    override fun addUsuario(nombre: String, correo: String) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(correo.isNotBlank()){ "El correo no puede estar vacío." }
        dao.insertarCampo(nombre.trim(), correo.trim())
    }

    /**
     * Recupera todos los usuarios registrados en la base de datos.
     *
     * @return Una lista de objetos [Usuario] que representan todos los usuarios.
     */
    override fun obtenerUsuarios(): List<Usuario> = dao.getAll()

    override fun obtenerUsuario(id: Int): Usuario? {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.getById(id)
    }


    override fun actualizarUsuario(nombre: String, id: Int): Boolean {
        require(id > 0){ "El ID debe ser mayor que 0." }
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        return dao.updateUsuario(nombre, id)
    }

    override fun eliminarPorId(id: Int): Boolean {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.deleteById(id)
    }
}