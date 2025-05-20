package es.prog2425.ejerciciosBD9_1.service.transacciones

import java.sql.Connection

class TransaccionLineasService: ITransaccionLineasService {
    override fun agregarLineaPedidoTransaccion(
        cantidad: Int,
        precio: Double,
        idPedido: Int,
        idProducto: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun agregarLineaPedidoTransaccion(
        cantidad: Int,
        precio: Double,
        idPedido: Int,
        idProducto: Int,
        conn: Connection
    ) {
        TODO("Not yet implemented")
    }

    override fun eliminarLineaPedidoTransaccion(id: Int) {
        TODO("Not yet implemented")
    }

    override fun eliminarLineaPedidoTransaccion(id: Int, conn: Connection) {
        TODO("Not yet implemented")
    }

    override fun actualizarLineaPedidoTransaccion(id: Int, precio: Double) {
        TODO("Not yet implemented")
    }

    override fun actualizarLineaPedidoTransaccion(id: Int, precio: Double, conn: Connection) {
        TODO("Not yet implemented")
    }
}