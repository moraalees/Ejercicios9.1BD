package es.prog2425.ejerciciosBD9_1.ui

interface IEntradaSalida {
    fun mostrar(msj: String)
    fun mostrarError(msj: String)
    fun limpiarPantalla(lineas: Int)
    fun saltoLinea()
    fun pausa()
}