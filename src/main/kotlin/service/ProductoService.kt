package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.model.Producto

class ProductoService(private val dao: ProductoDAOH2) : IProductoService {
    /**
     * Añade una nueva línea de pedido utilizando valores individuales.
     *
     * @param idPedido ID del pedido al que se añadirá la línea.
     * @param idProducto ID del producto que se está agregando.
     * @param cantidad Cantidad de productos.
     * @param precio Precio unitario del producto.
     *
     * Valida que todos los valores sean positivos antes de llamar al metodo del DAO correspondiente.
     * Llama al DAO para insertar la nueva línea de pedido en la base de datos.
     */
    override fun addProducto(nombre: String, precio: Double, stock: Int) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        require(stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(nombre.trim(), precio, stock)
    }

    /**
     * Añade una nueva línea de pedido utilizando un objeto `LineaPedido`.
     *
     * @param lineaPedido Objeto que contiene los datos necesarios para registrar una línea de pedido.
     *
     * Valida internamente que todos los campos del objeto sean positivos.
     * Llama al DAO para insertar la nueva línea de pedido en la base de datos.
     */
    override fun addProducto(producto: Producto) {
        require(producto.nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(producto.precio > 0){ "El precio debe ser mayor que 0." }
        require(producto.stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(producto.nombre.trim(), producto.precio, producto.stock)
    }

    override fun obtenerProductos(): List<Producto> = dao.getAll()
}