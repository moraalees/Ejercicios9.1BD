package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.SQLException

fun main() {
    val connection = try{
        DatabaseTienda.getConnection()
    } catch (e: SQLException){
        println("Se ha producido un error en la BD: ${e.message}")
        null
    } catch (e: Exception) {
        println("Excepción inesperada: ${e.message}")
        null
    }
    if (connection != null){
        println("Conexión exitosa.")
        try {
            DatabaseTienda.closeConnection(connection)
            println("Se ha cerrado la conexión exitósamente.")
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }
}