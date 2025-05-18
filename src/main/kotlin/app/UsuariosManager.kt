package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.service.IUsuarioService
import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida

class UsuariosManager(private val servicio: IUsuarioService, private val ui: IEntradaSalida) {

    private var salir = false

    fun programaUsuarios() {
        while (!salir) {
            ui.limpiarPantalla(20)
            mostrarMenu()
            ui.saltoLinea()
            ui.mostrar("Elige una opción (1 - 6):")
            val entrada = readln()
            when (entrada){
                "1" -> ""
                "2" -> ""
                "3" -> ""
                "4" -> ""
                "5" -> ""
                "6" -> ""
                else -> ui.mostrarError("Opción inválida.")
            }
        }
    }

    private fun mostrarMenu(){
        println(""""
                    ----MENÚ USUARIOS----
                    1. Listar Usuarios
                    2. Buscar Usuario por ID
                    3. Añadir Usuario
                    4. Eliminar Usuario
                    5. Actualizar Username
                    6. Salir
                """.trimIndent()
        )
    }

}