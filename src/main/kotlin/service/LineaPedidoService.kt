package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.model.LineaPedido

class LineaPedidoService(private val dao: LineaPedidoDAOH2) : ILineaPedidoService {
    /**
     * Añade una nueva línea de pedido utilizando valores individuales.
     *
     * @param idPedido ID del pedido al que se añadirá la línea.
     * @param idProducto ID del producto que se está agregando.
     * @param cantidad Cantidad de productos.
     * @param precio Precio unitario del producto.
     *
     * Valida que todos los valores sean positivos antes de llamar al metodo del DAO correspondiente.
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
     * Añade una nueva línea de pedido utilizando un objeto `LineaPedido`.
     *
     * @param lineaPedido Objeto que contiene los datos necesarios para registrar una línea de pedido.
     *
     * Valida internamente que todos los campos del objeto sean positivos.
     * Llama al DAO para la inserción en la tabla.
     */
    override fun addLineaPedido(lineaPedido: LineaPedido) {
        require(lineaPedido.idPedido > 0) { "El ID debe de ser mayor que 0." }
        require(lineaPedido.idProducto > 0) { "El ID debe de ser mayor que 0." }
        require(lineaPedido.cantidad > 0) { "La cantidad debe de ser mayor que 0." }
        require(lineaPedido.precio > 0) { "El precio debe de ser mayor que 0." }
        dao.insertarCampo(lineaPedido.idPedido, lineaPedido.idProducto, lineaPedido.cantidad, lineaPedido.precio)
    }

    /**
     * Obtiene todas las líneas de pedido registradas en la base de datos.
     *
     * @return Una lista de objetos [LineaPedido].
     *
     * Llama al DAO para la obtención de líneas.
     */
    override fun obtenerLineasPedido(): List<LineaPedido> = dao.getAll()

    /**
     * Obtiene todas las líneas de pedido asociadas a un pedido específico.
     *
     * @param id Identificador del pedido. Debe ser mayor que 0.
     * @return Lista de objetos [LineaPedido] correspondientes al pedido indicado.
     * @throws IllegalArgumentException Si el ID es menor o igual a 0.
     */
    override fun obtenerLineaPedidoByPedidoId(id: Int): List<LineaPedido> {
        require(id > 0){ "El id debe ser mayor que 0." }
        return dao.getLineasByPedido(id)
    }

    override fun modificarProductoYPrecioPorLinea(id: Int, idProducto: Int) {
        require(id > 0){ "El id debe ser mayor que 0." }
        require(idProducto > 0){ "El id debe ser mayor que 0." }
        dao.modifyProductoYPrecioPorLinea(id, idProducto)
    }
}