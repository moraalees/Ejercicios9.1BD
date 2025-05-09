package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.model.Pedido

class PedidoService(private val dao: PedidoDAOH2) : IPedidoService {
    override fun addPedido(idUsuario: Int, precio: Double){
        require(idUsuario > 0){ "El ID debe ser mayor que 0." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        dao.insertarCampo(idUsuario, precio)
    }

    override fun addPedido(pedido: Pedido){
        require(pedido.idUsuario > 0){ "El ID debe ser mayor que 0." }
        require(pedido.precioTotal > 0){ "El precio debe ser mayor que 0." }
        dao.insertarCampo(pedido.idUsuario, pedido.precioTotal)
    }
}