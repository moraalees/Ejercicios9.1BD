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
     * Añade un nuevo usuario utilizando un objeto `Usuario`.
     *
     * @param usuario Objeto que contiene los datos necesarios para registrar un usuario.
     *
     * Valida internamente que el nombre y el correo no estén vacíos.
     * Llama al DAO para insertar el nuevo usuario en la base de datos.
     */
    override fun addUsuario(usuario: Usuario) {
        require(usuario.nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(usuario.correo.isNotBlank()){ "El correo no puede estar vacío." }
        dao.insertarCampo(usuario.nombre.trim(), usuario.correo.trim())
    }

    /**
     * Recupera todos los usuarios registrados en la base de datos.
     *
     * @return Una lista de objetos [Usuario] que representan todos los usuarios.
     */
    override fun obtenerUsuarios(): List<Usuario> = dao.getAll()

    /**
     * Obtiene una lista de usuarios que han comprado un producto específico.
     *
     * @param nombre Nombre del producto que se desea consultar.
     * @return Lista de objetos [Usuario] que han comprado el producto indicado.
     * @throws IllegalArgumentException Si el nombre del producto está vacío o en blanco.
     */
    override fun obtenerUsuarioPorProductoComprado(nombre: String): List<Usuario>{
        require(nombre.isNotBlank()){ "El nombre del producto no puede estar vacío." }
        return dao.getUsuariosByProductoComprado(nombre)
    }
}