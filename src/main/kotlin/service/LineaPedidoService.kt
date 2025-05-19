package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.model.LineaPedido

/**
 * Servicio que gestiona las operaciones relacionadas con [LineaPedido].
 *
 * Esta clase valida los datos antes de llamar a operaciones del DAO correspondiente.
 *
 * @property dao Implementación de acceso a datos para [LineaPedido].
 */
class LineaPedidoService(private val dao: LineaPedidoDAOH2) : ILineaPedidoService {
    /**
     * Añade una nueva línea de pedido utilizando valores individuales.
     *
     * @param idPedido ID del pedido al que se añadirá la línea. Debe ser mayor que 0.
     * @param idProducto ID del producto que se está agregando. Debe ser mayor que 0.
     * @param cantidad Cantidad de productos. Debe ser mayor que 0.
     * @param precio Precio unitario del producto. Debe ser mayor que 0.
     *
     * @throws IllegalArgumentException Si alguno de los valores es menor o igual a 0.
     */
    override fun addLineaPedido(
        idPedido: Int,
        idProducto: Int,
        cantidad: Int,
        precio: Double
    ) {
        require(idPedido > 0) { "El ID debe de ser mayor que 0." }
        require(idProducto > 0) { "El ID debe de ser mayor que 0." }
        require(cantidad > 0) { "La cantidad debe de ser mayor que 0." }
        require(precio > 0) { "El precio debe de ser mayor que 0." }
        dao.insertarCampo(idPedido, idProducto, cantidad, precio)
    }

    /**
     * Obtiene todas las líneas de pedido registradas.
     *
     * @return Lista de objetos [LineaPedido].
     */
    override fun obtenerLineasPedido(): List<LineaPedido> = dao.getAll()

    /**
     * Obtiene una [LineaPedido] específica por su ID.
     *
     * @param id Identificador de [LineaPedido]. Debe ser mayor que 0.
     * @return La línea de pedido encontrada o null si no existe.
     * @throws IllegalArgumentException Si el ID es menor o igual a 0.
     */
    override fun obtenerLineaById(id: Int): LineaPedido? {
        require(id > 0){ "El ID debe ser mayor que 0." }
        return dao.getById(id)
    }

    /**
     * Obtiene todas las [LineaPedido] de un pedido específico por su ID.
     *
     * @param id Identificador del pedido. Debe ser mayor que 0.
     * @return Lista de líneas de pedido asociadas.
     * @throws IllegalArgumentException Si el ID es menor o igual a 0.
     */
    override fun obtenerLineaPedidoByPedidoId(id: Int): List<LineaPedido> {
        require(id > 0){ "El id debe ser mayor que 0." }
        return dao.getLineasByPedido(id)
    }

    /**
     * Actualiza el precio de una [LineaPedido] específica.
     *
     * @param precio Nuevo precio a establecer. Debe ser mayor que 0.
     * @param id ID de la [LineaPedido] a modificar. Debe ser mayor que 0.
     * @return Boolean: true si la actualización fue exitosa o false si no se modificó ningún registro.
     * @throws IllegalArgumentException Si el precio o el ID son inválidos; menores que 0.
     */
    override fun actualizarLinea(precio: Double, id: Int): Boolean {
        require(precio > 0){ "El precio debe ser mayor que 0." }
        require(id > 0){ "El id debe ser mayor que 0." }
        return dao.updateLinea(precio, id)
    }

    /**
     * Elimina una [LineaPedido] por su ID.
     *
     * @param id ID de la [LineaPedido] a eliminar. Debe ser mayor que 0.
     * @return Boolean: true si la eliminación fue exitosa o false si no se eliminó ninguna línea.
     * @throws IllegalArgumentException Si el ID es inválido.
     */
    override fun eliminarLinea(id: Int): Boolean {
        require(id > 0){ "El id debe ser mayor que 0." }
        return dao.deleteLinea(id)
    }
}