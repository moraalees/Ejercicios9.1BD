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
    val producto = "Abanico"
    val nuevoPrecio = 69.42
    val idLinea = 3
    val idProducto = 2

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)
    val lineaPedidoDao = LineaPedidoDAOH2()
    val lineaPedidoService = LineaPedidoService(lineaPedidoDao)

    //Aquí se modifica el precio del abanico
    productoService.modificarProducto(producto, nuevoPrecio)
    //Aquí se modifica la línea de pedido don ID 3
    lineaPedidoService.modificarProductoYPrecioPorLinea(idLinea, idProducto)
}