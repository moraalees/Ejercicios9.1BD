package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda

fun main() {
    val connection = DatabaseTienda.getConnection()

    DatabaseTienda.closeConnection(connection)

}