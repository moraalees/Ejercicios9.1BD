package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.model.Historial
import es.prog2425.ejerciciosBD9_1.service.IHistorialService
import es.prog2425.ejerciciosBD9_1.service.IProductoService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar el menú de operaciones relacionadas con productos.
 *
 * @param servicio Servicio que proporciona las operaciones CRUD sobre productos.
 * @param ui Interfaz para la interacción con el usuario.
 */
class ProductosManager(
    private val servicio: IProductoService,
    private val ui: IEntradaSalida,
    private val servicioHistorial: IHistorialService
) {

    /**
     * Inicia el ciclo principal del programa de productos.
     * Muestra el menú y responde a la opción seleccionada por el usuario.
     */
    fun programaProductos(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción (o escribe):")
            when (entrada){
                "1", "list" -> mostrarProductos()
                "2", "busca" -> obtenerProducto()
                "3", "agregar" -> agregarProducto()
                "4", "delete" -> eliminarProducto()
                "5", "actu" -> actualizarPrecio()
                "6", "salir" -> salir = salirPrograma()
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
                1. Listar Productos (list)
                2. Buscar Producto por ID (busca)
                3. Añadir Producto (agregar)
                4. Eliminar Producto Por ID (delete)
                5. Actualizar Precio (actu)
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
            servicioHistorial.agregarCampo(Historial("Se listaron los productos"))
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
        var salirObtener = false

        while (!salirObtener){

            ui.saltoLinea()
            val idProducto = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()

            if (idProducto == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val producto = servicio.obtenerPorId(idProducto)

                    if (producto != null){
                        ui.mostrar("ID: ${producto.id}, Nombre: ${producto.nombre}, Precio: ${producto.precio}, Stock: ${producto.stock}")
                        servicioHistorial.agregarCampo(Historial("Se consultó el producto con ID $idProducto"))
                    } else {
                        ui.mostrarError("El producto no existe...")
                        servicioHistorial.agregarCampo(Historial("Error al obtener el producto con ID $idProducto"))
                    }
                } catch (e: SQLException){
                    ui.mostrarError("Problemas al buscar el producto: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Problema inesperado: ${e.message}")
                }
            }
            val pregunta = ui.entrada("¿Quiere buscar otro producto? (s/n)").trim()
            if (pregunta == "n"){
                salirObtener = true
            }
        }
    }

    /**
     * Solicita al usuario los datos de un nuevo producto y lo agrega al sistema.
     */
    private fun agregarProducto(){
        var salirAgregar = false

        while (!salirAgregar){
            ui.saltoLinea()

            val entrada = ui.entrada("Ingrese el número de productos a agregar:").toIntOrNull()

            if (entrada == null || entrada < 1){
                ui.mostrarError("El número debe de ser mayor a 0 o no nulo...")
            } else {
                var contador = 1
                while (contador <= entrada){
                    contador ++
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
                        servicioHistorial.agregarCampo(Historial("Se añadió un nuevo producto: $nombre, $precio, $stock"))
                    } catch (e: IllegalArgumentException){
                        ui.mostrarError("Argumento inválido: ${e.message}")
                    } catch (e: Exception){
                        ui.mostrarError("Error inesperado: ${e.message}")
                    } catch (e: SQLException){
                        ui.mostrarError("Error al añadir el producto: ${e.message}")
                    }
                }
            }
            val pregunta = ui.entrada("¿Quiere agregar otro producto? (s/n)").trim()
            if (pregunta == "n"){
                salirAgregar = true
            }
        }
    }

    /**
     * Elimina un producto existente basado en su ID.
     */
    private fun eliminarProducto(){
        var salirEliminar = false

        while (!salirEliminar){
            ui.saltoLinea()
            val idProducto = ui.entrada("Ingrese el ID del producto a eliminar: ").toIntOrNull()

            if (idProducto == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val eliminado = servicio.eliminarProducto(idProducto)
                    if (eliminado) {
                        ui.mostrar("Producto eliminado con éxito!")
                        servicioHistorial.agregarCampo(Historial("Se eliminó el producto con ID $idProducto"))
                    } else {
                        ui.mostrar("No se encontró un producto con ese ID.")
                        servicioHistorial.agregarCampo(Historial("Error al eliminar un producto con ID $idProducto"))
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al eliminar el producto: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
            val pregunta = ui.entrada("¿Quiere eliminar otro producto? (s/n)").trim()
            if (pregunta == "n"){
                salirEliminar = true
            }
        }
    }

    /**
     * Actualiza el precio de un producto existente.
     */
    private fun actualizarPrecio(){
        var salirActu = false

        while (!salirActu){
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
                            servicioHistorial.agregarCampo(Historial("Se modificó el precio del producto con ID $idProducto"))
                        } else {
                            ui.mostrar("No se encontró un producto con ese ID.")
                            servicioHistorial.agregarCampo(Historial("Error al modificar el producto con ID $idProducto"))
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
            val pregunta = ui.entrada("¿Quiere actualizar el precio de otro producto? (s/n)").trim()
            if (pregunta == "n"){
                salirActu = true
            }
        }
    }

    /**
     * Sale del programa de gestión de productos.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        return true
    }
}