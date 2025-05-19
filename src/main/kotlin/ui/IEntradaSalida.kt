package es.prog2425.ejerciciosBD9_1.ui

/**
 * Interfaz que dicta todos los métodos de la consola
 */
interface IEntradaSalida {
    fun mostrar(msj: String)
    fun mostrarError(msj: String)
    fun limpiarPantalla(lineas: Int)
    fun saltoLinea()
    fun pausa()
    fun entrada(msj: String): String
}