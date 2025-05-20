package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.model.Historial
import es.prog2425.ejerciciosBD9_1.service.IHistorialService
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
    private val servicioUsuario: IUsuarioService,
    private val servicioHistorial: IHistorialService
) {

    /**
     * Inicia el bucle del menú principal para la gestión de pedidos.
     */
    fun programaPedidos(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción (o escribe):").trim()
            when (entrada){
                "1", "list"      -> mostrarPedidos()
                "2", "busca"     -> obtenerProducto()
                "3", "agregar"   -> agregarPedido()
                "4", "delete"    -> eliminarPedido()
                "5", "actu"      -> actualizarPrecio()
                "6", "importe"   -> obtenerImporte()
                "7", "username"  -> pedidosPorUsuario()
                "8", "salir"     -> salir = salirPrograma()
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
                1. Listar Pedidos (list)
                2. Buscar Pedido por ID (busca)
                3. Añadir Pedido (agregar)
                4. Eliminar Pedido Por ID (delete)
                5. Actualizar Precio (actu)
                6. Obtener Importe Total de Usuario (importe)
                7. Obtener Pedidos por Usuario (username)
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
            servicioHistorial.agregarCampo(Historial("Se listaron los pedidos"))
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
        var salirObtener = false

        while (!salirObtener){
            ui.saltoLinea()
            val idPedido = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()

            if (idPedido == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val pedido = servicio.obtenerPorId(idPedido)

                    if (pedido != null){
                        ui.mostrar("ID: ${pedido.id}, Precio: ${pedido.precioTotal}, ID Usuario: ${pedido.idUsuario}")
                        servicioHistorial.agregarCampo(Historial("Se consultó el pedido con ID $idPedido"))
                    } else {
                        ui.mostrarError("El pedido no existe...")
                        servicioHistorial.agregarCampo(Historial("Error al obtener el pedido con ID $idPedido"))
                    }
                } catch (e: SQLException){
                    ui.mostrarError("Problemas al buscar el pedido: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Problema inesperado: ${e.message}")
                }
            }

            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirObtener = true
            }
        }
    }

    /**
     * Permite al usuario añadir un nuevo pedido si el usuario relacionado existe.
     */
    private fun agregarPedido(){
        var salirAgregar = false

        while (!salirAgregar){
            ui.saltoLinea()

            val entrada = ui.entrada("Ingrese el número de pedidos a agregar:").toIntOrNull()

            if (entrada == null || entrada < 1){
                ui.mostrarError("El número debe de ser mayor a 0 o no nulo...")
            } else {
                var contador = 1
                while (contador <= entrada){
                    contador++
                    ui.saltoLinea()
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
                                servicioHistorial.agregarCampo(Historial("Se agregó un pedido: $precio, $idUsuario"))
                            } catch (e: SQLException) {
                                ui.mostrarError("Error al agregar el pedido: ${e.message}")
                            } catch (e: Exception) {
                                ui.mostrarError("Error inesperado: ${e.message}")
                            }
                        }
                    }
                }
            }
            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirAgregar = true
            }
        }
    }

    /**
     * Elimina un pedido existente según el ID ingresado por el usuario.
     */
    private fun eliminarPedido(){
        var salirEliminar = false

        while (!salirEliminar){
            ui.saltoLinea()
            val idPedido = ui.entrada("Ingrese el ID del pedido a eliminar: ").toIntOrNull()

            if (idPedido == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val eliminado = servicio.eliminarPedidoConLinea(idPedido)
                    if (eliminado) {
                        ui.mostrar("Pedido eliminado con éxito!")
                        servicioHistorial.agregarCampo(Historial("Se eliminó el pedido con ID $idPedido"))
                    } else {
                        ui.mostrar("No se encontró un pedido con ese ID...")
                        servicioHistorial.agregarCampo(Historial("Error al eliminar un pedido con ID $idPedido"))
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al eliminar el pedido: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }

            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirEliminar = true
            }
        }
    }

    /**
     * Permite modificar el precio total de un pedido existente.
     */
    private fun actualizarPrecio(){
        var salirActu = false

        while (!salirActu){
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
                            servicioHistorial.agregarCampo(Historial("Se modificó el precio del pedido con ID $idPedido"))
                        } else {
                            ui.mostrar("No se encontró un pedido con ese ID.")
                            servicioHistorial.agregarCampo(Historial("Error al modificar un pedido con ID $idPedido"))
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
            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirActu = true
            }
        }
    }

    /**
     * Obtiene y muestra el importe total acumulado de pedidos de un usuario específico.
     */
    private fun obtenerImporte(){
        var salirImporte = false

        while (!salirImporte){

            ui.saltoLinea()
            val idUsuario = ui.entrada("Introduce el ID del usuario: ").toIntOrNull()
            if (idUsuario == null){
                ui.mostrarError("El ID debe ser válido...")
            } else {
                try {
                    val total = servicio.obtenerImportePedidosPorUsuario(idUsuario)
                    ui.mostrar("El importe total de pedidos para el usuario $idUsuario es: $total.")
                    servicioHistorial.agregarCampo(Historial("Se buscó el importe de los pedidos del usuario con ID $idUsuario"))
                } catch (e: SQLException) {
                    ui.mostrarError("Error al consultar el importe total: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }
            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirImporte = true
            }
        }
    }

    /**
     * Muestra todos los pedidos asociados a un usuario ingresado por el usuario.
     */
    private fun pedidosPorUsuario(){
        var salirObtenerUsuario = false

        while (!salirObtenerUsuario){

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
            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirObtenerUsuario = true
            }
        }
    }

    /**
     * Finaliza el programa y sale del menú de gestión de pedidos.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        return true
    }
}