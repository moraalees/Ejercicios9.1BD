package es.prog2425.ejerciciosBD9_1.ui

/**
 * Clase que maneja la interacción con el usuario por consola.
 *
 * Implementa funciones como mostrar mensajes, capturar entradas, limpiar pantalla,
 * o pausar la ejecución.
 */
class UI : IEntradaSalida {
    /**
     * Muestra un mensaje por pantalla.
     *
     * @param msj El mensaje a mostrar.
     */
    override fun mostrar(msj: String) {
        println(msj)
    }

    /**
     * Muestra un mensaje de error con un formato específico.
     *
     * @param msj El mensaje de error a mostrar.
     */
    override fun mostrarError(msj: String) {
        saltoLinea()
        mostrar("*-_ERROR_-* $msj")
    }

    /**
     * Limpia la pantalla simulando varias líneas en blanco.
     *
     * @param lineas Número de líneas en blanco a imprimir.
     */
    override fun limpiarPantalla(lineas: Int) {
        var contador = 0
        while (contador < lineas){
            contador++
            saltoLinea()
        }
    }

    /**
     * Muestra una línea en blanco.
     */
    override fun saltoLinea() {
        mostrar("")
    }

    /**
     * Pausa la ejecución hasta que el usuario pulse ENTER.
     */
    override fun pausa() {
        saltoLinea()
        mostrar("Pulse ENTER...")
        val valor = readln()
    }

    /**
     * Solicita una entrada al usuario con un mensaje previo.
     *
     * @param msj Mensaje de entrada que se muestra al usuario.
     * @return La entrada del usuario como cadena de texto.
     */
    override fun entrada(msj: String): String {
        mostrar(msj)
        return readln()
    }
}