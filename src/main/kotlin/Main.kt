package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.service.LineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService
import es.prog2425.ejerciciosBD9_1.data.db.DataSourceFactory
import es.prog2425.ejerciciosBD9_1.data.db.Mode
import es.prog2425.ejerciciosBD9_1.model.Usuario

fun main() {
    val dataSource = try {
        DataSourceFactory.getDataSource(Mode.HIKARI)
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Problemas al crear el DataSource: ${e.message}")
    }

    val usuario = Usuario(1, "Reinaldo Girúndez", "reingir@mail.com")
    val nombreUsuario = "Facundo Pérez"

    val usuarioDao = UsuarioDAOH2(dataSource)
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2(dataSource)
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2(dataSource)
    val pedidoService = PedidoService(pedidoDao)
    val lineaPedidoDao = LineaPedidoDAOH2(dataSource)
    val lineaPedidoService = LineaPedidoService(lineaPedidoDao)

    usuarioService.addUsuario(usuario)
    val pedidos = pedidoService.obtenerPedidosPorNombreUsuario(nombreUsuario)
    println(pedidos)
}