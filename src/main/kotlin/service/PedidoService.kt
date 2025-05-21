package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.IPedidoDAO
import es.prog2425.ejerciciosBD9_1.model.Pedido

/**
 * Servicio que maneja las operaciones relacionadas con [Pedido].
 *
 * Este servicio valida los datos antes de llamar al DAO (PedidoDAOH2).
 *
 * @property dao Se encarga de las operaciones sobre [Pedido].
 */
class PedidoService(private val dao: IPedidoDAO) : IPedidoService {

    /**
     * Añade un nuevo [Pedido] utilizando valores individuales.
     *
     * @param idUsuario ID del usuario que realiza el [Pedido].
     * @param precio Precio total del [Pedido].
     *
     * Valida que ambos valores sean positivos antes de llamar al metodo del DAO correspondiente.
     * Llama al DAO para insertar el nuevo pedido en la base de datos.
     */
    override fun addPedido(idUsuario: Int, precio: Double){
        require(idUsuario > 0){ "El ID debe ser mayor que 0." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        dao.insertarCampo(idUsuario, precio)
    }

    /**
     * Obtiene un [Pedido] específico a partir de su ID.
     *
     * @param id ID del [Pedido] a buscar. Debe ser mayor que 0.
     * @return El [Pedido] encontrado o null si no existe.
     *
     * @throws IllegalArgumentException Si el ID es inválido.
     */
    override fun obtenerPorId(id: Int): Pedido? {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.getById(id)
    }

    /**
     * Recupera todos los [Pedido] almacenados en la base de datos.
     *
     * @return Una lista de objetos [Pedido] que representan todos los pedidos existentes.
     */
    override fun obtenerPedidos(): List<Pedido> = dao.getAll()

    /**
     * Obtiene el importe total de todos los [Pedido] realizados por un usuario específico.
     *
     * @param nombre Nombre del usuario del cual se desea obtener el total de sus [Pedido].
     * @return Suma total del importe de los [Pedido] realizados por el usuario.
     * @throws IllegalArgumentException Si el nombre está vacío o en blanco.
     */
    override fun obtenerImportePedidosPorUsuario(id: Int): Double{
        require(id > 0) { "El ID debe ser mayor a 0." }
        return dao.getTotalImporteById(id)
    }

    /**
     * Elimina un [Pedido] específico junto con todas sus líneas asociadas en la base de datos.
     *
     * @param id ID del [Pedido] que se desea eliminar.
     * @throws IllegalArgumentException Si el ID proporcionado no es mayor que 0.
     */
    override fun eliminarPedidoConLinea(id: Int): Boolean {
        require(id > 0) { "El ID debe ser mayor que 0." }
        return dao.deletePedidoConLineas(id)
    }

    /**
     * Obtiene todos los [Pedido] asociados a un usuario a partir de su ID.
     *
     * @param id ID del usuario. Debe ser mayor que 0.
     * @return Lista de [Pedido] realizados por ese usuario.
     *
     * @throws IllegalArgumentException Si el ID es inválido.
     */
    override fun obtenerPedidosPorNombreUsuario(id: Int): List<Pedido> {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.getPedidosPorNombreUsuario(id)
    }

    /**
     * Actualiza el precio total de un [Pedido] específico.
     *
     * @param precioTotal Nuevo precio. Debe ser mayor que 0.
     * @param id ID del [Pedido] que se desea actualizar. Debe ser mayor que 0.
     * @return Boolean: true si la actualización fue exitosa o false si no se modificó ningún [Pedido].
     *
     * @throws IllegalArgumentException Si el precio o ID son inválidos.
     */
    override fun actualizarPedido(precioTotal: Double, id: Int): Boolean {
        require(precioTotal > 0){ "El precio debe ser mayor que 0." }
        require(id > 0) { "El ID debe ser mayor que 0." }
        return dao.updatePedido(precioTotal, id)
    }
}