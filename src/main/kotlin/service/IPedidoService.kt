package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Pedido
import java.sql.Connection

interface IPedidoService {
    fun addPedido(idUsuario: Int, precio: Double)
    fun addPedido(pedido: Pedido)
    fun addPedido(connection: Connection, idUsuario: Int, precio: Double)
    fun addPedido(connection: Connection, pedido: Pedido)
}