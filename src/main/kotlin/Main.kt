package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.app.ProductosManager
import es.prog2425.ejerciciosBD9_1.app.UsuariosManager
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.data.db.DataSourceFactory
import es.prog2425.ejerciciosBD9_1.data.db.Mode
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService
import es.prog2425.ejerciciosBD9_1.ui.UI

fun main() {
    val consola = UI()

    val dataSource = try {
        DataSourceFactory.getDataSource(Mode.HIKARI)
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Problemas al crear el DataSource: ${e.message}")
    }

    val usuarioDao = UsuarioDAOH2(dataSource)
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2(dataSource)
    val productoService = ProductoService(productoDao)

    ProductosManager(productoService, consola).programaProductos()

}