package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.ILineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.IPedidoService
import es.prog2425.ejerciciosBD9_1.service.IProductoService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase que simula el comportamiento del programa del menú de Lineas de Pedido.
 *
 * @property service Servicio que proporciona operaciones en la BD sobre las líneas de pedido.
 * @property servicioPedido Servicio que permite consultar pedidos existentes.
 * @property servicioProducto Servicio que permite consultar productos existentes.
 * @property ui Interfaz usada para interactuar con el usuario.
 */
class LineaPedidosManager(
    private val service: ILineaPedidoService,
    private val servicioPedido: IPedidoService,
    private val servicioProducto: IProductoService,
    private val ui: IEntradaSalida
) {

    //Variable Boolean que simula cuando se debe de salir del menú
    private var salir = false

    /**
     * Función principal del programa de gestión de líneas de pedido.
     * Muestra un menú interactivo hasta que el usuario decida salir.
     */
    fun programaLineaPedidos() {
        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada){
                "1" -> mostrarLineas()
                "2" -> obtenerLinea()
                "3" -> agregarLinea()
                "4" -> eliminarLinea()
                "5" -> actualizarLinea()
                "6" -> obtenerLineaPorPedido()
                "7" -> salirPrograma()
                else -> ui.mostrarError("Opción no válida...")
            }
            ui.pausa()
        }
    }

    /**
     * Muestra todas las líneas de pedido disponibles en el sistema.
     */
    private fun mostrarLineas(){
        ui.saltoLinea()
        try {
            val lineas = service.obtenerLineasPedido()
            if (lineas.isEmpty()){
                ui.mostrar("No hay ninguna línea registrada...")
            } else {
                lineas.forEach { ui.mostrar("ID: ${it.id}, Cantidad: ${it.cantidad}, Precio: ${it.precio}, ID Pedido: ${it.idPedido}, ID Producto: ${it.idProducto}")}
            }
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al obtener las líneas: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    /**
     * Muestra el menú principal de opciones al usuario.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
                ----MENÚ LÍNEA PEDIDOS----
                1. Listar Líneas
                2. Buscar Línea por ID
                3. Añadir Línea
                4. Eliminar Línea Por ID
                5. Actualizar Precio
                6. Obtener Línea por Pedido
                7. Salir
                """.trimIndent()
        )
    }

    /**
     * Solicita un ID de línea al usuario y muestra la línea correspondiente, si existe.
     */
    private fun obtenerLinea(){
        ui.saltoLinea()
        val idLinea = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()

        if (idLinea == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val linea = service.obtenerLineaById(idLinea)

                if (linea != null){
                    ui.mostrar("ID: ${linea.id}, Cantidad: ${linea.cantidad}, Precio: ${linea.precio}, ID Pedido: ${linea.idPedido}, ID Producto: ${linea.idProducto}")
                } else {
                    ui.mostrarError("La línea no existe...")
                }
            } catch (e: SQLException){
                ui.mostrarError("Problemas al buscar la línea de pedidos: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Problema inesperado: ${e.message}")
            }
        }
    }

    /**
     * Permite al usuario crear una nueva línea de pedido tras ingresar los datos requeridos.
     */
    private fun agregarLinea(){
        ui.saltoLinea()
        val cantidad = ui.entrada("Ingrese la cantidad de la nueva línea de pedidos: ").toIntOrNull()
        val precio = ui.entrada("Ingrese el precio de la nueva línea de pedidos: ").toDoubleOrNull()
        val idPedido = ui.entrada("Ingrese el ID del pedido relacionado: ").toIntOrNull()
        val idProducto = ui.entrada("Ingrese el ID del producto relacionado: ").toIntOrNull()

        if (cantidad == null || precio == null || idPedido == null || idProducto == null){
            ui.mostrarError("Algún dato fue inválido...")
        } else {
            val pedidoId = servicioPedido.obtenerPorId(idPedido)
            val productoId = servicioProducto.obtenerPorId(idProducto)
            if (pedidoId == null || productoId == null){
                ui.mostrarError("Un ID no existe...")
            } else {
                try {
                    service.addLineaPedido(idPedido, idProducto, cantidad, precio)
                    ui.mostrar("Línea de Pedido agregada éxitosamente!")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al agregar el pedido: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Elimina una línea de pedido según el ID introducido por el usuario.
     * Muestra mensajes de éxito o error según corresponda.
     */
    private fun eliminarLinea(){
        ui.saltoLinea()
        val idLinea = ui.entrada("Ingrese el ID de la línea a eliminar: ").toIntOrNull()

        if (idLinea == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val eliminado = service.eliminarLinea(idLinea)
                if (eliminado) {
                    ui.mostrar("Línea eliminada con éxito!")
                } else {
                    ui.mostrar("No se encontró ninguna línea con ese ID...")
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Argumentos inválidos: ${e.message}")
            } catch (e: SQLException) {
                ui.mostrarError("Error al eliminar la línea: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Actualiza el precio de una línea de pedido a partir del ID y nuevo precio proporcionado por el usuario.
     */
    private fun actualizarLinea(){
        ui.saltoLinea()
        val idLinea = ui.entrada("Ingrese el ID de la línea a modificar: ").toIntOrNull()

        if (idLinea == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            val precio = ui.entrada("Ingrese el nuevo precio de la línea: ").toDoubleOrNull()
            if (precio == null) {
                ui.mostrarError("El precio es nulo...")
            } else {
                try {
                    val actualizado = service.actualizarLinea(precio, idLinea)
                    if (actualizado) {
                        ui.mostrar("Línea modificada con éxito!")
                    } else {
                        ui.mostrar("No se encontró ninguna línea con ese ID...")
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al modificar la línea: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Obtiene y muestra todas las líneas de pedido asociadas a un pedido específico,
     * cuyo ID es ingresado por el usuario.
     */
    private fun obtenerLineaPorPedido(){
        ui.saltoLinea()
        val idPedido = ui.entrada("Introduce el ID del pedido: ").toIntOrNull()
        if (idPedido == null){
            ui.mostrarError("El ID no puede ser nulo...")
        } else {
            try {
                val lineas = service.obtenerLineaPedidoByPedidoId(idPedido)

                if (lineas.isEmpty()) {
                    ui.mostrar("No se encontraron líneas para el pedido con ID $idPedido...")
                } else {
                    ui.mostrar("Líneas del pedido $idPedido:")
                    lineas.forEach { linea ->
                        ui.mostrar("ID: ${linea.id}, Cantidad: ${linea.cantidad}, Precio: ${linea.precio}, ID Producto: ${linea.idProducto}, ID Pedido: ${linea.idPedido}")
                    }
                }
            } catch (e: SQLException) {
                ui.mostrarError("Error al consultar las líneas: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Finaliza el bucle del programa y muestra un mensaje de salida.
     */
    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}