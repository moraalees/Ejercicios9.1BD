package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.ui.IEntradaSalida

/**
 * Clase que simula el programa principal.
 *
 * @param managerUsuarios Gestor de las operaciones relacionadas con los usuarios.
 * @param managerProductos Gestor de las operaciones relacionadas con los productos.
 * @param managerPedidos Gestor de las operaciones relacionadas con los pedidos.
 * @param managerLineas Gestor de las operaciones relacionadas con las líneas de pedido.
 * @param ui Interfaz para la interacción con el usuario.
 */
class ProgramaManager(
    private val managerUsuarios: UsuariosManager,
    private val managerProductos: ProductosManager,
    private val managerPedidos: PedidosManager,
    private val managerLineas: LineaPedidosManager,
    private val managerHistorial: HistorialManager,
    private val ui: IEntradaSalida
) {

    /**
     * Inicia el programa principal, mostrando el menú y
     * permitiendo navegar por los diferentes módulos.
     */
    fun programa(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.entrada("Elige una opción (o escribe):").trim()
            when (entrada){
                "1", "usuarios" -> managerUsuarios.programaUsuarios()
                "2", "productos" -> managerProductos.programaProductos()
                "3", "pedidos" -> managerPedidos.programaPedidos()
                "4", "lineas" -> managerLineas.programaLineaPedidos()
                "5", "historial" -> managerHistorial.programaHistorial()
                "6", "salir" -> salir = salirPrograma()
                else -> ui.mostrarError("Opción no válida...")
            }
        }
    }

    /**
     * Muestra el menú principal del sistema.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ APP----
            1. Usuarios
            2. Productos
            3. Pedidos
            4. Líneas de Pedidos
            5. Historial
            6. Salir
        """.trimIndent())
    }

    /**
     * Marca la bandera de salida y muestra un mensaje de cierre del programa.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del programa...")
        return true
    }
}