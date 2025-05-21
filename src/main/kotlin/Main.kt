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
        println("Conexión exitosa")

        DatabaseTienda.closeConnection(connection)
        println("Se ha cerrado la conexión exitosamente.")
    }
}