package es.prog2425.ejerciciosBD9_1.service.transacciones

import java.sql.Connection

interface ITransaccionLineasService {
    fun agregarLineaPedidoTransaccion()
    fun agregarLineaPedidoTransaccion(conn: Connection)
    fun eliminarLineaPedidoTransaccion(id: Int)
    fun eliminarLineaPedidoTransaccion(id: Int, conn: Connection)
    fun actualizarLineaPedidoTransaccion(id: Int)
    fun actualizarLineaPedidoTransaccion(id: Int, conn: Connection)
}