package es.prog2425.ejerciciosBD9_1.ui

class UI : IEntradaSalida {
    override fun mostrar(msj: String) {
        println(msj)
    }

    override fun mostrarError(msj: String) {
        saltoLinea()
        println("*-_ERROR_-* $msj")
    }

    override fun limpiarPantalla(lineas: Int) {
        var contador = 0
        while (contador < lineas){
            contador++
            saltoLinea()
        }
    }

    override fun saltoLinea() {
        println("")
    }


    override fun pausa() {
        saltoLinea()
        println("Pulse ENTER...")
        val valor = readln()
    }
}