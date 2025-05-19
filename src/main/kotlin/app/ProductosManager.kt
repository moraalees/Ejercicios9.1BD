package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.ui.UI
import java.sql.SQLException

class ProductosManager(private val servicio: ProductoService, private val ui: UI) {
    private var salir = false

    fun programaProductos(){
        while (!salir) {
            ui.limpiarPantalla(20)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada){
                "1" -> ""
                "2" -> ""
                "3" -> ""
                "4" -> ""
                "5" -> ""
                "6" -> salirPrograma()
                else -> ui.mostrarError("Opción inválida...")
            }
        }
    }

    private fun mostrarProductos(){
        ui.saltoLinea()
        try {
            val productos = servicio.obtenerProductos()
            if (productos.isEmpty()){
                ui.mostrar("No hay ningún producto...")
            } else {
                productos.forEach { ui.mostrar("ID: ${it.id}, Nombre: ${it.nombre}, Precio: ${it.precio}, Stock: ${it.stock}") }
            }
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al obtener productos: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    private fun mostrarMenu(){
        ui.mostrar("""
                ----MENÚ USUARIOS----
                1. Listar Productos
                2. Buscar Producto por ID
                3. Añadir Producto
                4. Eliminar Producto Por ID
                5. Actualizar Precio
                6. Salir
                """.trimIndent()
        )
    }

    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}