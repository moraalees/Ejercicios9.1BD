package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.model.Historial
import es.prog2425.ejerciciosBD9_1.service.IHistorialService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

class HistorialManager(
    private val servicio: IHistorialService,
    private val ui: IEntradaSalida
) {

    fun programaHistorial(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción (o escribe):").trim()
            when (entrada){
                "1", "list" -> listarHistorial()
                "2", "delete" -> eliminarHistorial()
                "3", "salir" -> salir = salirPrograma()
                else -> ui.mostrarError("Opción no válida...")
            }
        }
    }

    fun mostrarMenu(){
        ui.mostrar("""
            1. Listar Historial (list)
            2. Eliminar Historial (delete)
            3. Salir
        """.trimIndent())
    }

    private fun listarHistorial(){
        ui.saltoLinea()
        try {
            val historial = servicio.getAllCeldas()
            if (historial.isEmpty()){
                ui.mostrar("No hay ninguna acción registrada...")
            } else {
                historial.forEach { ui.mostrar("Mensaje: ${it.mensaje}, Fecha: ${it.fechaHora} ") }
            }
            servicio.agregarCampo(Historial("Se listaron las acciones del historial"))
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al obtener el historial: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    private fun eliminarHistorial(){
        ui.saltoLinea()
        try {
            servicio.deleteAll()
            ui.mostrar("Historial eliminado correctamente.")
            servicio.agregarCampo(Historial("Se eliminó el historial completo"))
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al eliminar el historial: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del programa...")
        return true
    }

}