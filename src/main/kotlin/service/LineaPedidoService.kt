package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2

class LineaPedidoService(private val dao: LineaPedidoDAOH2) : ILineaPedidoService {
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
}