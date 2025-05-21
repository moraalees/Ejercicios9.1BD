package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.app.InicializadorTablas
import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import es.prog2425.ejerciciosBD9_1.model.Pedido
import es.prog2425.ejerciciosBD9_1.model.Producto
import es.prog2425.ejerciciosBD9_1.model.Usuario
import es.prog2425.ejerciciosBD9_1.service.LineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService
import java.sql.Connection
import java.sql.SQLException

fun main() {
    try {
        InicializadorTablas.crearTablas()
    } catch (e: SQLException) {
        println(e.message)
    } catch (e: Exception) {
        println(e.message)
    }

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)
    val lineaDao = LineaPedidoDAOH2()
    val lineaService = LineaPedidoService(lineaDao)
    val usuario = Usuario(1, "Facundo Pérez", "facuper@mail.com")
    val producto = Producto(1, "Ventilador", 10.0, 2)
    val pedido = Pedido(1, 160.0, 2)
    val lineaPedido = LineaPedido(1, 1, 10.0, 1, 1)

    try {
        usuarioService.addUsuario(usuario)
        usuarioService.addUsuario("Ataulfo Rodríguez", "ataurod@mail.com")
        usuarioService.addUsuario("Cornelio Ramírez", "Cornram@mail.com")

        productoService.addProducto(producto)
        productoService.addProducto("Abanico", 150.0, 47)
        productoService.addProducto("Estufa", 24.99, 1)
    } catch (e: SQLException) {
        println(e.message)
    } catch (e: Exception) {
        println(e.message)
    }

    //Inicio transaccion
    var connection: Connection? = null
    try {
        connection = DatabaseTienda.getConnection()
        connection.autoCommit = false

        pedidoService.addPedido(connection, pedido)
        pedidoService.addPedido(connection, 1, 20.0)
        pedidoService.addPedido(connection, 2, 150.0)

        lineaService.addLineaPedido(lineaPedido)
        lineaService.addLineaPedido(connection, 1, 2, 1, 150.0)
        lineaService.addLineaPedido(2, 1, 2, 20.0)
        lineaService.addLineaPedido(3, 2, 1, 150.0)

        connection.commit()
    } catch (e: SQLException){
        println(e.message)
    } catch (e: Exception){
        println(e.message)
        try{
            connection?.rollback()
        } catch (e: SQLException){
            println("Error en el rollback: ${e.message}")
        }
    } finally {
        if (connection != null){
            try {
                DatabaseTienda.closeConnection(connection)
                println("Se cerró la conexión exitosamente.")
            } catch (e: SQLException) {
                println("Error al cerrar la conexión: ${e.message}")
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
            }
        }
    }
    //Fin transacción
}