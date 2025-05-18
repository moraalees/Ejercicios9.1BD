package es.prog2425.ejerciciosBD9_1.ui

class UI : IEntradaSalida {
    override fun mostrarError(msj: String) {
        println("*-_ERROR_-* $msj")
    }

    override fun limpiarPantalla(lineas: Int) {
        var contador = 0
        while (contador < lineas){
            contador++
            println("")
        }
    }

    override fun pausa() {
        println("Pulse ENTER...")
        val valor = readln()
    }
}