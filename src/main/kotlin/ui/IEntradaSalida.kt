package es.prog2425.ejerciciosBD9_1.ui

interface IEntradaSalida {
    fun mostrarError(msj: String)
    fun limpiarPantalla(lineas: Int)
    fun pausa()
}