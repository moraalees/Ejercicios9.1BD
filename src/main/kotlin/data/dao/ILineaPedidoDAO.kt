package es.prog2425.ejerciciosBD9_1.data.dao

interface ILineaPedidoDAO {
    fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
}