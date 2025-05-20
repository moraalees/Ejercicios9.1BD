package es.prog2425.ejerciciosBD9_1.service.transacciones

import java.sql.Connection

interface ITransaccionPedidosService {
    fun agregarPedidoTransaccion(precio: Double, idUsuario: Int)
    fun agregarPedidoTransaccion(precio: Double, idUsuario: Int, conn: Connection)
    fun eliminarPedidoTransaccion(id: Int)
    fun eliminarPedidoTransaccion(id: Int, conn: Connection)
    fun actualizarPedidoTransaccion(id: Int, precio: Double)
    fun actualizarPedidoTransaccion(id: Int, precio: Double, conn: Connection)
}