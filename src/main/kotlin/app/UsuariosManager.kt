package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.model.Historial
import es.prog2425.ejerciciosBD9_1.service.IHistorialService
import es.prog2425.ejerciciosBD9_1.service.IUsuarioService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar el menú de operaciones relacionadas con los usuarios.
 *
 * @param servicio Servicio que proporciona las operaciones en la BD sobre usuarios.
 * @param ui Interfaz para la interacción con el usuario.
 */
class UsuariosManager(
    private val servicio: IUsuarioService,
    private val ui: IEntradaSalida,
    private val servicioHistorial: IHistorialService
) {

    /**
    * Inicia el programa de gestión de usuarios, mostrando el menú principal en bucle.
    */
    fun programaUsuarios() {
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción (o escribe):").trim()
            when (entrada) {
                "1", "list"     -> mostrarUsuarios()
                "2", "busca"    -> buscarUsuario()
                "3", "agregar"  -> agregarUsuario()
                "4", "delete"   -> eliminarUsuario()
                "5", "actu"     -> modificarUsuario()
                "6", "salir"    -> salir = salirPrograma()
                else -> ui.mostrarError("Opción inválida.")
            }
            ui.pausa()
        }
    }

    /**
     * Muestra el menú de opciones disponibles para la gestión de usuarios.
     */
    private fun mostrarMenu() {
        ui.mostrar(
            """
                ----MENÚ USUARIOS----
                1. Listar Usuarios (list)
                2. Buscar Usuario por ID (busca)
                3. Añadir Usuario (agregar)
                4. Eliminar Usuario Por ID (delete)
                5. Actualizar Username (actu)
                6. Salir
                """.trimIndent()
        )
    }

    /**
     * Muestra todos los usuarios almacenados en el sistema.
     */
    private fun mostrarUsuarios() {
        ui.saltoLinea()
        try {
            val usuarios = servicio.obtenerUsuarios()
            if (usuarios.isEmpty()) {
                ui.mostrar("No hay ningún usuario.")
            } else {
                usuarios.forEach { ui.mostrar("ID: ${it.id}, Nombre: ${it.nombre}, Correo: ${it.correo}") }
            }
            servicioHistorial.agregarCampo(Historial("Se listaron los usuarios"))
        } catch (e: SQLException) {
            ui.mostrarError("Problemas al obtener usuarios: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Problema inesperado: ${e.message}")
        }
    }

    /**
     * Busca un usuario por su ID e imprime su información si se encuentra.
     */
    private fun buscarUsuario() {
        var salirBuscar = false

        while (!salirBuscar){
            ui.saltoLinea()
            val idUsuario = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()
            if (idUsuario == null) {
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val usuario = servicio.obtenerUsuario(idUsuario)

                    if (usuario != null) {
                        ui.mostrar("ID: ${usuario.id}, Nombre: ${usuario.nombre}, Correo: ${usuario.correo}")
                        servicioHistorial.agregarCampo(Historial("Se consultó el usuario con ID $idUsuario"))
                    } else {
                        ui.mostrarError("El usuario no existe...")
                        servicioHistorial.agregarCampo(Historial("Búsqueda fallida del usuario con ID $idUsuario"))
                    }
                } catch (e: SQLException) {
                    ui.mostrarError("Problemas al buscar el usuario: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Problema inesperado: ${e.message}")
                }
            }
            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirBuscar = true
            }
        }
    }

    /**
     * Solicita los datos del nuevo usuario al usuario y lo agrega al sistema.
     */
    private fun agregarUsuario() {
        var salirAgregar = false

        while (!salirAgregar){
            ui.saltoLinea()

            val entrada = ui.entrada("Ingrese el número de usuarios a agregar:").toIntOrNull()

            if (entrada == null || entrada < 1){
                ui.mostrarError("El número debe de ser mayor a 0 o no nulo...")
            } else {
                var contador = 1
                while (contador <= entrada){
                    contador ++
                    ui.saltoLinea()
                    val nombreUsuario = ui.entrada("Ingrese el nombre del nuevo usuario: ")
                    val correoUsuario = ui.entrada("Ingrese el E-mail del nuevo usuario: ")

                    try {
                        servicio.addUsuario(nombreUsuario, correoUsuario)
                        ui.mostrar("Usuario añadido con éxito!")
                        servicioHistorial.agregarCampo(Historial("Se añadió un nuevo usuario: $nombreUsuario, $correoUsuario"))
                    } catch (e: IllegalArgumentException) {
                        ui.mostrarError("Argumentos inválidos: ${e.message}")
                    } catch (e: Exception) {
                        ui.mostrarError("Error inesperado: ${e.message}")
                    } catch (e: SQLException) {
                        ui.mostrarError("Error al añadir el usuario: ${e.message}")
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
     * Elimina un usuario del sistema mediante su ID.
     */
    private fun eliminarUsuario(){
        var salirEliminar = false

        while (!salirEliminar){
            ui.saltoLinea()
            val idUsuario = ui.entrada("Ingrese el ID a eliminar: ").toIntOrNull()

            if (idUsuario == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                try {
                    val eliminado = servicio.eliminarPorId(idUsuario)
                    if (eliminado) {
                        ui.mostrar("Usuario eliminado con éxito!")
                        servicioHistorial.agregarCampo(Historial("Se eliminó el usuario con ID $idUsuario"))
                    } else {
                        ui.mostrar("No se encontró un usuario con ese ID.")
                        servicioHistorial.agregarCampo(Historial("Error al eliminar un usuario con ID $idUsuario"))
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al eliminar el usuario: ${e.message}")
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
     * Actualiza el nombre de usuario de un usuario existente.
     */
    private fun modificarUsuario(){
        var salirModificar = false

        while (!salirModificar){
            ui.saltoLinea()
            val idUsuario = ui.entrada("Ingrese el ID del usuario a modificar: ").toIntOrNull()

            if (idUsuario == null){
                ui.mostrarError("El ID es nulo...")
            } else {
                val username = ui.entrada("Ingrese el nuevo nombre de usuario: ")
                try {
                    val actualizado = servicio.actualizarUsuario(username, idUsuario)
                    if (actualizado) {
                        ui.mostrar("Usuario actualizado con éxito!")
                        servicioHistorial.agregarCampo(Historial("Se modificó el nombre de usuario con ID $idUsuario"))
                    } else {
                        ui.mostrar("No se encontró un usuario con ese ID.")
                        servicioHistorial.agregarCampo(Historial("Error al modificar un nombre de usuario con ID $idUsuario"))
                    }
                } catch (e: IllegalArgumentException) {
                    ui.mostrarError("Argumentos inválidos: ${e.message}")
                } catch (e: SQLException) {
                    ui.mostrarError("Error al modificar el nombre: ${e.message}")
                } catch (e: Exception) {
                    ui.mostrarError("Error inesperado: ${e.message}")
                }
            }

            val pregunta = ui.entrada("¿Quiere buscar otra línea? (s/n)").trim()
            if (pregunta == "n"){
                salirModificar = true
            }
        }
    }

    /**
     * Sale del menú de gestión de usuarios.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        return true
    }
}