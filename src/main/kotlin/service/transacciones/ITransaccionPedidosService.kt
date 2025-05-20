package es.prog2425.ejerciciosBD9_1.service.transacciones

import java.sql.Connection

interface ITransaccionPedidosService {
    fun agregarPedidoTransaccion()
    fun agregarPedidoTransaccion(conn: Connection)
    fun eliminarPedidoTransaccion(id: Int)
    fun eliminarPedidoTransaccion(id: Int, conn: Connection)
    fun actualizarPedidoTransaccion(id: Int)
    fun actualizarPedidoTransaccion(id: Int, conn: Connection)
}