package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.PedidoDAOH2
import es.prog2425.ejerciciosBD9_1.model.Pedido

class PedidoService(private val dao: PedidoDAOH2) : IPedidoService {
    /**
     * Añade un nuevo pedido utilizando valores individuales.
     *
     * @param idUsuario ID del usuario que realiza el pedido.
     * @param precio Precio total del pedido.
     *
     * Valida que ambos valores sean positivos antes de llamar al metodo del DAO correspondiente.
     * Llama al DAO para insertar el nuevo pedido en la base de datos.
     */
    override fun addPedido(idUsuario: Int, precio: Double){
        require(idUsuario > 0){ "El ID debe ser mayor que 0." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        dao.insertarCampo(idUsuario, precio)
    }

    /**
     * Añade un nuevo pedido utilizando un objeto `Pedido`.
     *
     * @param pedido Objeto que contiene los datos necesarios para registrar un pedido.
     *
     * Valida internamente que ambos campos del objeto sean positivos.
     * Llama al DAO para insertar el nuevo pedido en la base de datos.
     */
    override fun addPedido(pedido: Pedido){
        require(pedido.idUsuario > 0){ "El ID debe ser mayor que 0." }
        require(pedido.precioTotal > 0){ "El precio debe ser mayor que 0." }
        dao.insertarCampo(pedido.idUsuario, pedido.precioTotal)
    }

    /**
     * Recupera todos los pedidos almacenados en la base de datos.
     *
     * @return Una lista de objetos [Pedido] que representan todos los pedidos existentes.
     */
    override fun obtenerPedidos(): List<Pedido> = dao.getAll()

    /**
     * Obtiene el importe total de todos los pedidos realizados por un usuario específico.
     *
     * @param nombre Nombre del usuario del cual se desea obtener el total de sus pedidos.
     * @return Suma total del importe de los pedidos realizados por el usuario.
     * @throws IllegalArgumentException Si el nombre está vacío o en blanco.
     */
    override fun obtenerPedidoPorUsuario(nombre: String): Double{
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        return dao.getTotalImporteByNombreUsuario(nombre)
    }

    override fun eliminarPedidoConLinea(id: Int) {
        require(id > 0) { "El id debe ser mayor que 0." }
        dao.deletePedidoConLineas(id)
    }
}