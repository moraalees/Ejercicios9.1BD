package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.app.LineaPedidosManager
import es.prog2425.ejerciciosBD9_1.app.PedidosManager
import es.prog2425.ejerciciosBD9_1.app.ProductosManager
import es.prog2425.ejerciciosBD9_1.app.ProgramaManager
import es.prog2425.ejerciciosBD9_1.app.UsuariosManager
import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.data.db.DataSourceFactory
import es.prog2425.ejerciciosBD9_1.data.db.Mode
import es.prog2425.ejerciciosBD9_1.service.LineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService
import es.prog2425.ejerciciosBD9_1.ui.UI

/**
 * Función principal del programa
 *
 * En esta función se llaman y realizan las acciones necesarias para que el programa empiece
 */
fun main() {
    val consola = UI()

    //Aquí se llama al Data Source Hikari para usarlo en las clases DAO
    val dataSource = try {
        DataSourceFactory.getDataSource(Mode.HIKARI)
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Problemas al crear el DataSource: ${e.message}")
    }

    //Se crean todos los servicios para luego inyectarlos en el programa principal
    val usuarioDao = UsuarioDAOH2(dataSource)
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2(dataSource)
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2(dataSource)
    val pedidoService = PedidoService(pedidoDao)
    val lineaDao = LineaPedidoDAOH2(dataSource)
    val lineaService = LineaPedidoService(lineaDao)

    ProgramaManager(
        UsuariosManager(usuarioService, consola),
        ProductosManager(productoService, consola),
        PedidosManager(pedidoService, consola ,usuarioService),
        LineaPedidosManager(lineaService, pedidoService, productoService, consola),
        consola).programa()
}