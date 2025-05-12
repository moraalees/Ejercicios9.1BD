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
    val nombre = "Cornelio Ramírez"
    val precio = 24.99
    val id = 3

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)
    val lineaPedidoDao = LineaPedidoDAOH2()
    val lineaPedidoService = LineaPedidoService(lineaPedidoDao)

    //Eliminar el usuario Cornelio
    usuarioService.eliminarUsuario(nombre)
    //Eliminar el producto de 24.99
    productoService.eliminarProducto(precio)
    //Eliminar el pedido de id 3
    pedidoService.eliminarPedidoConLinea(id)
}