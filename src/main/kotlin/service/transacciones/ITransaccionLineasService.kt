package es.prog2425.ejerciciosBD9_1.service.transacciones

import java.sql.Connection

interface ITransaccionLineasService {
    fun agregarLineaPedidoTransaccion(cantidad: Int, precio: Double, idPedido: Int, idProducto: Int)
    fun agregarLineaPedidoTransaccion(cantidad: Int, precio: Double, idPedido: Int, idProducto: Int, conn: Connection)
    fun eliminarLineaPedidoTransaccion(id: Int)
    fun eliminarLineaPedidoTransaccion(id: Int, conn: Connection)
    fun actualizarLineaPedidoTransaccion(id: Int, precio: Double)
    fun actualizarLineaPedidoTransaccion(id: Int, precio: Double, conn: Connection)
}