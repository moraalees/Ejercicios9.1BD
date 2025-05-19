package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.IProductoService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar el menú de operaciones relacionadas con productos.
 *
 * @param servicio Servicio que proporciona las operaciones CRUD sobre productos.
 * @param ui Interfaz para la interacción con el usuario.
 */
class ProductosManager(private val servicio: IProductoService, private val ui: IEntradaSalida) {
    //Variable Boolean que simula cuando se debe de salir del menú
    private var salir = false

    /**
     * Inicia el ciclo principal del programa de productos.
     * Muestra el menú y responde a la opción seleccionada por el usuario.
     */
    fun programaProductos(){
        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada){
                "1" -> mostrarProductos()
                "2" -> obtenerProducto()
                "3" -> agregarProducto()
                "4" -> eliminarProducto()
                "5" -> actualizarPrecio()
                "6" -> salirPrograma()
                else -> ui.mostrarError("Opción inválida...")
            }
            ui.pausa()
        }
    }

    /**
     * Muestra el menú de opciones disponibles para la gestión de productos.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
                ----MENÚ PRODUCTOS----
                1. Listar Productos
                2. Buscar Producto por ID
                3. Añadir Producto
                4. Eliminar Producto Por ID
                5. Actualizar Precio
                6. Salir
                """.trimIndent()
        )
    }

    /**
     * Obtiene todos los productos y los muestra al usuario.
     */
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

    /**
     * Busca un producto por su ID y lo muestra si existe.
     */
    private fun obtenerProducto(){
        ui.saltoLinea()
        val idProducto = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()

        if (idProducto == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val producto = servicio.obtenerPorId(idProducto)

                if (producto != null){
                    ui.mostrar("ID: ${producto.id}, Nombre: ${producto.nombre}, Precio: ${producto.precio}, Stock: ${producto.stock}")
                } else {
                    ui.mostrarError("El producto no existe...")
                }
            } catch (e: SQLException){
                ui.mostrarError("Problemas al buscar el producto: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Problema inesperado: ${e.message}")
            }
        }
    }

    /**
     * Solicita al usuario los datos de un nuevo producto y lo agrega al sistema.
     */
    private fun agregarProducto(){
        ui.saltoLinea()
        val nombre = ui.entrada("Ingrese el nombre del nuevo producto: ")
        val precio = ui.entrada("Ingrese el precio del nuevo producto: ").toDoubleOrNull()
        val stock = ui.entrada("Ingrese el stock del nuevo producto: ").toIntOrNull()
        if (precio == null || stock == null) {
            ui.mostrarError("Precio o stock inválido...")
            return
        }
        try {
            servicio.addProducto(nombre, precio, stock)
            ui.mostrar("Producto añadido con éxito!")
        } catch (e: IllegalArgumentException){
            ui.mostrarError("Argumento inválido: ${e.message}")
        } catch (e: Exception){
            ui.mostrarError("Error inesperado: ${e.message}")
        } catch (e: SQLException){
            ui.mostrarError("Error al añadir el producto: ${e.message}")
        }
    }

    /**
     * Elimina un producto existente basado en su ID.
     */
    private fun eliminarProducto(){
        ui.saltoLinea()
        val idProducto = ui.entrada("Ingrese el ID del producto a eliminar: ").toIntOrNull()

        if (idProducto == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val eliminado = servicio.eliminarProducto(idProducto)
                if (eliminado) {
                    ui.mostrar("Producto eliminado con éxito!")
                } else {
                    ui.mostrar("No se encontró un producto con ese ID.")
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Argumentos inválidos: ${e.message}")
            } catch (e: SQLException) {
                ui.mostrarError("Error al eliminar el producto: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Actualiza el precio de un producto existente.
     */
    private fun actualizarPrecio(){
        ui.saltoLinea()
        val idProducto = ui.entrada("Ingrese el ID del producto a modificar: ").toIntOrNull()

        if (idProducto == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            val precio = ui.entrada("Ingrese el nuevo precio del producto: ").toDoubleOrNull()
            if (precio == null) {
                ui.mostrarError("El precio es nulo...")
            } else {
                try {
                    val modificado = servicio.modificarProducto(idProducto, precio)
                    if (modificado) {
                        ui.mostrar("Producto modificado con éxito!")
                    } else {
                        ui.mostrar("No se encontró un producto con ese ID.")
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al modificar el producto: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Sale del programa de gestión de productos.
     */
    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}