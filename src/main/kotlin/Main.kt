package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.app.InicializadorTablas
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.model.Producto
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService

fun main() {
    InicializadorTablas.crearTablas()

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)

    /*
    usuarioService.addUsuario("Facundo Pérez", "facuper@mail.com")
    usuarioService.addUsuario("Ataulfo Rodríguez", "ataurod@mail.com")
    usuarioService.addUsuario("Cornelio Ramírez", "Cornram@mail.com")

    productoService.addProducto("Ventilador", 10.0, 2)
    productoService.addProducto("Abanico", 150.0, 47)
    productoService.addProducto("Estufa", 24.99, 1)

    pedidoService.addPedido(2, 160.0)
    pedidoService.addPedido(1, 20.0)
    pedidoService.addPedido(2, 150.0)
    */
}