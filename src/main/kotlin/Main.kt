package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.service.LineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService

fun main() {

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val usuarios = usuarioService.obtenerUsuarios()
    println(usuarios)

    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val productos = productoService.obtenerProductos()
    println(productos)

    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)
    val pedidos = pedidoService.obtenerPedidos()
    println(pedidos)

    val lineaPedidoDao = LineaPedidoDAOH2()
    val lineaPedidoService = LineaPedidoService(lineaPedidoDao)
    val lineasPedido = lineaPedidoService.obtenerLineasPedido()
    println(lineasPedido)
}