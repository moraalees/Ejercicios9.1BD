package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida

class ProgramaManager(
    private val managerUsuarios: UsuariosManager,
    private val managerProductos: ProductosManager,
    private val managerPedidos: PedidosManager,
    private val managerLineas: LineaPedidosManager,
    private val ui: IEntradaSalida
) {

    private var salir = false

    fun programa(){
        while (!salir) {
            ui.limpiarPantalla(20)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción:")
            when (entrada){
                "1" -> managerUsuarios.programaUsuarios()
                "2" -> managerProductos.programaProductos()
                "3" -> managerPedidos.programaPedidos()
                "4" -> managerLineas.programaLineaPedidos()
                "5" -> salirPrograma()
                else -> ui.mostrarError("Opción no válida...")
            }
        }
    }

    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ APP----
            1. Usuarios
            2. Productos
            3. Pedidos
            4. Líneas de Pedidos
            5. Salir
        """.trimIndent())
    }

    private fun salirPrograma(){
        ui.saltoLinea()
        ui.mostrar("Saliendo del programa...")
        salir = true
    }

}