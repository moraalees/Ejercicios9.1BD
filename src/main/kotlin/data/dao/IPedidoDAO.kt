package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Pedido
import java.sql.Connection

interface IPedidoDAO {
    fun insertarCampo(idUsuario: Int, precio: Double)
    fun insertarCampo(pedido: Pedido)
    fun insertarCampo(conn: Connection, idUsuario: Int, precio: Double)
    fun insertarCampo(conn: Connection, pedido: Pedido)
    fun getAll(): List<Pedido>
    fun getTotalImporteByNombreUsuario(nombre: String): Double
    fun deletePedidoConLineas(id: Int)
}