package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.IUsuarioService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase encargada de gestionar el menú de operaciones relacionadas con los usuarios.
 *
 * @param servicio Servicio que proporciona las operaciones en la BD sobre usuarios.
 * @param ui Interfaz para la interacción con el usuario.
 */
class UsuariosManager(private val servicio: IUsuarioService, private val ui: IEntradaSalida) {

    //Variable Boolean que simula cuando se debe de salir del menú
    private var salir = false

    /**
    * Inicia el programa de gestión de usuarios, mostrando el menú principal en bucle.
    */
    fun programaUsuarios() {
        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada) {
                "1" -> mostrarUsuarios()
                "2" -> buscarUsuario()
                "3" -> agregarUsuario()
                "4" -> eliminarUsuario()
                "5" -> modificarUsuario()
                "6" -> salirPrograma()
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
                1. Listar Usuarios
                2. Buscar Usuario por ID
                3. Añadir Usuario
                4. Eliminar Usuario Por ID
                5. Actualizar Username
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
        ui.saltoLinea()
        val idUsuario = ui.entrada("Introduce el ID para la búsqueda: ").toIntOrNull()
        if (idUsuario == null) {
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val usuario = servicio.obtenerUsuario(idUsuario)

                if (usuario != null) {
                    ui.mostrar("ID: ${usuario.id}, Nombre: ${usuario.nombre}, Correo: ${usuario.correo}")
                } else {
                    ui.mostrarError("El usuario no existe...")
                }
            } catch (e: SQLException) {
                ui.mostrarError("Problemas al buscar el usuario: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Problema inesperado: ${e.message}")
            }
        }
    }

    /**
     * Solicita los datos del nuevo usuario al usuario y lo agrega al sistema.
     */
    private fun agregarUsuario() {
        ui.saltoLinea()
        val nombreUsuario = ui.entrada("Ingrese el nombre del nuevo usuario: ")
        val correoUsuario = ui.entrada("Ingrese el E-mail del nuevo usuario: ")

        try {
            servicio.addUsuario(nombreUsuario, correoUsuario)
            ui.mostrar("Usuario añadido con éxito!")
        } catch (e: IllegalArgumentException) {
            ui.mostrarError("Argumentos inválidos: ${e.message}")
        } catch (e: Exception) {
            ui.mostrarError("Error inesperado: ${e.message}")
        } catch (e: SQLException) {
            ui.mostrarError("Error al añadir el usuario: ${e.message}")
        }
    }

    /**
     * Elimina un usuario del sistema mediante su ID.
     */
    private fun eliminarUsuario(){
        ui.saltoLinea()
        val idUsuario = ui.entrada("Ingrese el ID a eliminar: ").toIntOrNull()

        if (idUsuario == null){
            ui.mostrarError("El ID es nulo...")
        } else {
            try {
                val eliminado = servicio.eliminarPorId(idUsuario)
                if (eliminado) {
                    ui.mostrar("Usuario eliminado con éxito!")
                } else {
                    ui.mostrar("No se encontró un usuario con ese ID.")
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Argumentos inválidos: ${e.message}")
            } catch (e: SQLException) {
                ui.mostrarError("Error al eliminar el usuario: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Actualiza el nombre de usuario de un usuario existente.
     */
    private fun modificarUsuario(){
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
                } else {
                    ui.mostrar("No se encontró un usuario con ese ID.")
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError("Argumentos inválidos: ${e.message}")
            } catch (e: SQLException) {
                ui.mostrarError("Error al modificar el nombre: ${e.message}")
            } catch (e: Exception) {
                ui.mostrarError("Error inesperado: ${e.message}")
            }
        }
    }

    /**
     * Sale del menú de gestión de usuarios.
     */
    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del menú...")
        salir = true
    }
}