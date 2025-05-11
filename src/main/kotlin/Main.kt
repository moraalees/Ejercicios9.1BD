package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.data.dao.LineaPedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.service.LineaPedidoService
import es.prog2425.ejerciciosBD9_1.service.PedidoService
import es.prog2425.ejerciciosBD9_1.service.ProductoService
import es.prog2425.ejerciciosBD9_1.service.UsuarioService

fun main() {
    val idPedido = 1
    val nombre = "Ataulfo Rodríguez"
    val productoComprado = "Abanico"

    val usuarioDao = UsuarioDAOH2()
    val usuarioService = UsuarioService(usuarioDao)
    val productoDao = ProductoDAOH2()
    val productoService = ProductoService(productoDao)
    val pedidoDao = PedidoDAOH2()
    val pedidoService = PedidoService(pedidoDao)
    val lineaPedidoDao = LineaPedidoDAOH2()
    val lineaPedidoService = LineaPedidoService(lineaPedidoDao)


    //Aquí se buscan los usuarios que han comprado un abanico
    val usuariosProductos = usuarioService.obtenerUsuarioPorProductoComprado(productoComprado)
    println(usuariosProductos)

    //Aquí se busca el importe total de los pedidos por Ataulfo
    val importeTotal = pedidoService.obtenerPedidoPorUsuario(nombre)
    println("El importe total de pedidos por Ataulfo es de: $importeTotal")

    //Aquí se buscan las líneas de pedido cuyo id es de 1
    val lineas = lineaPedidoService.obtenerLineaPedidoByPedidoId(idPedido)
    println(lineas)

    /*
    //Aquí hay una parte que proporcionan todos los datos de todas las tablas
    val usuarios = usuarioService.obtenerUsuarios()
    println(usuarios)
    val productos = productoService.obtenerProductos()
    println(productos)
    val pedidos = pedidoService.obtenerPedidos()
    println(pedidos)
    val lineasPedido = lineaPedidoService.obtenerLineasPedido()
    println(lineasPedido)
    */
}