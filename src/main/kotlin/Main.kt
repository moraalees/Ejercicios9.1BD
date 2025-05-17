package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.db.DataSourceFactory
import es.prog2425.ejerciciosBD9_1.data.db.Mode
import es.prog2425.ejerciciosBD9_1.ui.UI

fun main() {
    val consola = UI()

    val dataSource = try {
        DataSourceFactory.getDataSource(Mode.HIKARI)
    } catch (e: IllegalStateException) {
        throw IllegalStateException("Problemas al crear el DataSource: ${e.message}")
    }
}