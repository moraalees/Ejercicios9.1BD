package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2

fun main() {

    val usuarioDao = UsuarioDAOH2()
    val usuarios = usuarioDao.getAll()
    println(usuarios)

    val productoDao = ProductoDAOH2()
    val productos = productoDao.getAll()
    println(productos)

    val pedidoDao = PedidoDAOH2()
    val pedidos = pedidoDao.getAll()
    println(pedidos)

    val lineaPedidoDao = LineaPedidoDAOH2()
    val lineasPedido = lineaPedidoDao.getAll()
    println(lineasPedido)

}