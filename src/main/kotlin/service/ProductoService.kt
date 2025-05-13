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

    /**
     * Recupera todos los productos disponibles en la base de datos.
     *
     * @return Una lista de objetos [Producto] que representan todos los productos existentes.
     */
    override fun obtenerProductos(): List<Producto> = dao.getAll()

    /**
     * Elimina un producto de la base de datos según su precio.
     *
     * @param precio Precio del producto que se desea eliminar.
     * @throws IllegalArgumentException Si el precio no es mayor que 0.
     */
    override fun eliminarProducto(precio: Double) {
        require(precio > 0){ "El precio debe ser mayor que 0 €." }
        dao.deleteByPrecio(precio)
    }

    /**
     * Modifica el precio de un producto identificado por su nombre.
     *
     * @param nombre Nombre del producto que se desea actualizar.
     * @param precio Nuevo precio que se asignará al producto.
     * @throws IllegalArgumentException Si el nombre está vacío o si el precio es menor o igual que 0.
     */
    override fun modificarProducto(nombre: String, precio: Double) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío" }
        require(precio > 0) { "El precio no puede ser menor o igual que 0 €" }
        dao.modifyProducto(nombre, precio)
    }
}