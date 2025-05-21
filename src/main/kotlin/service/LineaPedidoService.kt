package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.Connection

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

    override fun addLineaPedido(
        conn: Connection,
        idPedido: Int,
        idProducto: Int,
        cantidad: Int,
        precio: Double
    ) {
        require(idPedido > 0) { "El ID debe de ser mayor que 0." }
        require(idProducto > 0) { "El ID debe de ser mayor que 0." }
        require(cantidad > 0) { "La cantidad debe de ser mayor que 0." }
        require(precio > 0) { "El precio debe de ser mayor que 0." }
        dao.insertarCampo(conn, idPedido, idProducto, cantidad, precio)
    }

    override fun addLineaPedido(conn: Connection, lineaPedido: LineaPedido) {
        require(lineaPedido.idPedido > 0) { "El ID debe de ser mayor que 0." }
        require(lineaPedido.idProducto > 0) { "El ID debe de ser mayor que 0." }
        require(lineaPedido.cantidad > 0) { "La cantidad debe de ser mayor que 0." }
        require(lineaPedido.precio > 0) { "El precio debe de ser mayor que 0." }
        dao.insertarCampo(conn, lineaPedido.idPedido, lineaPedido.idProducto, lineaPedido.cantidad, lineaPedido.precio)
    }
}