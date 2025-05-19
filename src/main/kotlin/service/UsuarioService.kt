package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.model.Usuario

/**
 * Servicio encargado de las operaciones relacionadas con [Usuario].
 *
 * Valida los datos de entrada antes de llamar al DAO correspondiente (UsuarioDAOH2).
 *
 * @property dao Gestiona las operaciones con la base de datos de [Usuario].
 */
class UsuarioService(private val dao: UsuarioDAOH2) : IUsuarioService {
    /**
     * Añade un nuevo [Usuario] utilizando valores individuales.
     *
     * @param nombre Nombre del [Usuario].
     * @param correo Dirección de correo electrónico del [Usuario].
     *
     * Valida que el nombre y el correo no estén vacíos antes de llamar al metodo del DAO correspondiente.
     * Llama al DAO para insertar el nuevo [Usuario] en la base de datos.
     */
    override fun addUsuario(nombre: String, correo: String) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(correo.isNotBlank()){ "El correo no puede estar vacío." }
        dao.insertarCampo(nombre.trim(), correo.trim())
    }

    /**
     * Recupera todos los objetos de [Usuario] registrados en la base de datos.
     *
     * @return Una lista de objetos [Usuario] que representan todos los usuarios.
     */
    override fun obtenerUsuarios(): List<Usuario> = dao.getAll()

    /**
     * Obtiene un [Usuario] específico por su ID.
     *
     * @param id Identificador del usuario. Debe ser mayor que 0.
     * @return El objeto [Usuario] si se encuentra o null si no existe.
     *
     * @throws IllegalArgumentException Si el ID no es válido.
     */
    override fun obtenerUsuario(id: Int): Usuario? {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.getById(id)
    }

    /**
     * Actualiza el nombre de un [Usuario] existente.
     *
     * @param nombre Nuevo nombre para el [Usuario]. No puede estar vacío.
     * @param id ID del [Usuario] que se desea actualizar. Debe ser mayor que 0.
     * @return Boolean: true si la actualización fue exitosa o false si no se encontró el [Usuario].
     *
     * @throws IllegalArgumentException Si el ID o el nombre son inválidos.
     */
    override fun actualizarUsuario(nombre: String, id: Int): Boolean {
        require(id > 0){ "El ID debe ser mayor que 0." }
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        return dao.updateUsuario(nombre, id)
    }

    /**
     * Elimina un [Usuario] de la base de datos por su ID.
     *
     * @param id Identificador del [Usuario] a eliminar. Debe ser mayor que 0.
     * @return Boolean: true si el usuario fue eliminado correctamente o false si no se encontró.
     *
     * @throws IllegalArgumentException Si el ID no es válido.
     */
    override fun eliminarPorId(id: Int): Boolean {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.deleteById(id)
    }
}