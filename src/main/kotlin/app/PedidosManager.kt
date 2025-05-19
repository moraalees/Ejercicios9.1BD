package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.IPedidoService
import es.prog2425.ejerciciosBD9_1.service.IUsuarioService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

class PedidosManager(
    private val servicio: IPedidoService,
    private val ui: IEntradaSalida,
    private val servicioUsuario: IUsuarioService
) {

    private var salir = false

    fun programaPedidos(){
        while (!salir) {
            ui.limpiarPantalla(20)
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

    private fun agregarPedido(){
        ui.saltoLinea()
        val precio = ui.entrada("Ingrese el precio del nuevo pedido: ").toDoubleOrNull()
        val idUsuario = ui.entrada("Ingrese el ID del nuevo pedido: ").toIntOrNull()
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

    private fun obtenerImporte(){
        ui.saltoLinea()
        val nombre = ui.entrada("Introduce el nombre del usuario: ")
        try {
            val total = servicio.obtenerImportePedidosPorUsuario(nombre)
            ui.mostrar("El importe total de pedidos para '$nombre' es: $total.")
        } catch (e: SQLException) {
            ui.mostrarError("Error al consultar el importe total: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Error inesperado: ${e.message}")
        }
    }

    private fun pedidosPorUsuario(){
        ui.saltoLinea()
        val nombre = ui.entrada("Introduce el nombre del usuario: ")

        try {
            val pedidos = servicio.obtenerPedidosPorNombreUsuario(nombre)
            if (pedidos.isEmpty()) {
                ui.mostrar("No se encontraron pedidos para el usuario '$nombre'...")
            } else {
                ui.mostrar("Pedidos de '$nombre':")
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

    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}