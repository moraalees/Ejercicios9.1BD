package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.IPedidoService
import es.prog2425.ejerciciosBD9_1.service.IUsuarioService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar la interacción con pedidos desde la terminal del IDE.
 *
 * @param servicio Servicio para operaciones con pedidos.
 * @param ui Interfaz para la interacción con el usuario.
 * @param servicioUsuario Servicio para operaciones con usuarios.
 */
class PedidosManager(
    private val servicio: IPedidoService,
    private val ui: IEntradaSalida,
    private val servicioUsuario: IUsuarioService
) {
    //Variable Boolean que simula cuando se debe de salir del menú
    private var salir = false

    /**
     * Inicia el bucle del menú principal para la gestión de pedidos.
     */
    fun programaPedidos(){
        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada){
                "1" -> mostrarPedidos()
                "2" -> obtenerProducto()
                "3" -> agregarPedido()
                "4" -> eliminarPedido()
                "5" -> actualizarPrecio()
                "6" -> obtenerImporte()
                "7" -> pedidosPorUsuario()
                "8" -> salirPrograma()
                else -> ui.mostrarError("Opción inválida...")
            }
            ui.pausa()
        }
    }

    /**
     * Muestra el menú con las opciones disponibles para gestionar pedidos.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
                ----MENÚ PEDIDOS----
                1. Listar Pedidos
                2. Buscar Pedido por ID
                3. Añadir Pedido
                4. Eliminar Pedido Por ID
                5. Actualizar Precio
                6. Obtener Importe Total de Usuario
                7. Obtener Pedidos por Usuario
                8. Salir
                """.trimIndent()
        )
    }

    /**
     * Muestra todos los pedidos registrados en la BD.
     */
    private fun mostrarPedidos(){
        ui.saltoLinea()
        try {
            val pedidos = servicio.obtenerPedidos()
            if (pedidos.isEmpty()){
                ui.mostrar("No hay ningún pedido...")
            } else {
                pedidos.forEach { ui.mostrar("ID: ${it.id}, Precio: ${it.precioTotal}, ID Usuario: ${it.idUsuario}")}
            }
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al obtener pedidos: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    /**
     * Obtiene un pedido por su ID y lo muestra si existe.
     */
    private fun obtenerProducto(){
        ui.saltoLinea()
        val idPedido = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()

        if (idPedido == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val pedido = servicio.obtenerPorId(idPedido)

                if (pedido != null){
                    ui.mostrar("ID: ${pedido.id}, Precio: ${pedido.precioTotal}, ID Usuario: ${pedido.idUsuario}")
                } else {
                    ui.mostrarError("El pedido no existe...")
                }
            } catch (e: SQLException){
                ui.mostrarError("Problemas al buscar el pedido: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Problema inesperado: ${e.message}")
            }
        }
    }

    /**
     * Permite al usuario añadir un nuevo pedido si el usuario relacionado existe.
     */
    private fun agregarPedido(){
        ui.saltoLinea()

        val entrada = ui.entrada("Ingrese el número de productos a agregar:").toIntOrNull()

        if (entrada == null || entrada < 1){
            ui.mostrarError("El número debe de ser mayor a 0 o no nulo...")
        } else {
            var contador = 1
            while (contador <= entrada){
                contador++
                val precio = ui.entrada("Ingrese el precio del nuevo pedido: ").toDoubleOrNull()
                val idUsuario = ui.entrada("Ingrese el ID del usuario relacionado: ").toIntOrNull()
                if (precio == null || idUsuario == null){
                    ui.mostrarError("El precio / ID de Usuario no puede ser nulo...")
                } else {
                    val usuarioId = servicioUsuario.obtenerUsuario(idUsuario)
                    if (usuarioId == null){
                        ui.mostrarError("No existe un usuario con ese ID...")
                    } else {
                        try {
                            servicio.addPedido(idUsuario ,precio)
                            ui.mostrar("Pedido agregado con éxito!")
                        } catch (e: SQLException) {
                            ui.mostrarError("Error al agregar el pedido: ${e.message}")
                        } catch (e: Exception) {
                            ui.mostrarError("Error inesperado: ${e.message}")
                        }
                    }
                }
            }
        }
    }

    /**
     * Elimina un pedido existente según el ID ingresado por el usuario.
     */
    private fun eliminarPedido(){
        ui.saltoLinea()
        val idPedido = ui.entrada("Ingrese el ID del pedido a eliminar: ").toIntOrNull()

        if (idPedido == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val eliminado = servicio.eliminarPedidoConLinea(idPedido)
                if (eliminado) {
                    ui.mostrar("Pedido eliminado con éxito!")
                } else {
                    ui.mostrar("No se encontró un pedido con ese ID...")
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Argumentos inválidos: ${e.message}")
            } catch (e: SQLException) {
                ui.mostrarError("Error al eliminar el pedido: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Permite modificar el precio total de un pedido existente.
     */
    private fun actualizarPrecio(){
        ui.saltoLinea()
        val idPedido = ui.entrada("Ingrese el ID del pedido a modificar: ").toIntOrNull()

        if (idPedido == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            val precio = ui.entrada("Ingrese el nuevo precio del pedido: ").toDoubleOrNull()
            if (precio == null) {
                ui.mostrarError("El precio es nulo...")
            } else {
                try {
                    val modificado = servicio.actualizarPedido(precio, idPedido)
                    if (modificado) {
                        ui.mostrar("Pedido modificado con éxito!")
                    } else {
                        ui.mostrar("No se encontró un pedido con ese ID.")
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al modificar el pedido: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Obtiene y muestra el importe total acumulado de pedidos de un usuario específico.
     */
    private fun obtenerImporte(){
        ui.saltoLinea()
        val idUsuario = ui.entrada("Introduce el ID del usuario: ").toIntOrNull()
        if (idUsuario == null){
            ui.mostrarError("El ID debe ser válido...")
        } else {
            try {
                val total = servicio.obtenerImportePedidosPorUsuario(idUsuario)
                ui.mostrar("El importe total de pedidos para el usuario $idUsuario es: $total.")
            } catch (e: SQLException) {
                ui.mostrarError("Error al consultar el importe total: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Muestra todos los pedidos asociados a un usuario ingresado por el usuario.
     */
    private fun pedidosPorUsuario(){
        ui.saltoLinea()
        val idUsuario = ui.entrada("Introduce el ID del usuario: ").toIntOrNull()
        if (idUsuario == null){
            ui.mostrarError("El ID es inválido...")
        } else {
            try {
                val pedidos = servicio.obtenerPedidosPorNombreUsuario(idUsuario)
                if (pedidos.isEmpty()) {
                    ui.mostrar("No se encontraron pedidos para el usuario con ID $idUsuario...")
                } else {
                    ui.mostrar("Pedidos del usuario con ID $idUsuario:")
                    pedidos.forEach { it ->
                        ui.mostrar("ID: ${it.id}, Precio: ${it.precioTotal}, ID Usuario: ${it.idUsuario}")
                    }
                }
            } catch (e: SQLException) {
                ui.mostrarError("Error al consultar los pedidos: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Finaliza el programa y sale del menú de gestión de pedidos.
     */
    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}