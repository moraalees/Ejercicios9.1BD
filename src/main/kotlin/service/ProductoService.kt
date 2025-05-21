package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.IProductoDAO
import es.prog2425.ejerciciosBD9_1.model.Producto

/**
 * Servicio que maneja las operaciones relacionadas con [Producto].
 *
 * Se encarga de validar los datos antes de llamar al DAO correspondiente (ProductoDAOH2).
 *
 * @property dao Instancia del DAO que proporciona acceso a la base de datos de [Producto].
 */
class ProductoService(private val dao: IProductoDAO) : IProductoService {

    /**
     * Añade un nuevo [Producto] al sistema con nombre, precio y stock.
     *
     * @param nombre Nombre del [Producto]. No debe estar vacío o en blanco.
     * @param precio Precio del [Producto]. Debe ser mayor que 0.
     * @param stock Cantidad en inventario. Debe ser mayor o igual que 0.
     *
     * @throws IllegalArgumentException Si alguno de los parámetros no cumple las condiciones.
     */
    override fun addProducto(nombre: String, precio: Double, stock: Int) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        require(stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(nombre.trim(), precio, stock)
    }

    /**
     * Recupera todos los productos disponibles en la base de datos.
     *
     * @return Una lista de objetos [Producto] que representan todos los productos existentes.
     */
    override fun obtenerProductos(): List<Producto> = dao.getAll()

    /**
     * Obtiene un [Producto] específico a partir de su ID.
     *
     * @param id Identificador único del [Producto]. Debe ser mayor que 0.
     * @return El [Producto] encontrado o null si no existe.
     *
     * @throws IllegalArgumentException Si el ID es menor o igual que 0.
     */
    override fun obtenerPorId(id: Int): Producto? {
        require(id > 0){ "El ID no puede ser menor o igual que 0." }
        return dao.getById(id)
    }

    /**
     * Elimina un [Producto] de la base de datos según su precio.
     *
     * @param precio Precio del [Producto] que se desea eliminar.
     * @throws IllegalArgumentException Si el precio no es mayor que 0.
     */
    override fun eliminarProducto(id: Int): Boolean {
        require(id > 0){ "El ID debe ser mayor que 0 €." }
        return dao.deleteByPrecio(id)
    }

    /**
     * Modifica el precio de un [Producto] identificado por su nombre.
     *
     * @param nombre Nombre del [Producto] que se desea actualizar.
     * @param precio Nuevo precio que se asignará al producto.
     * @throws IllegalArgumentException Si el nombre está vacío o si el precio es menor o igual que 0.
     */
    override fun modificarProducto(id: Int, precio: Double): Boolean {
        require(id > 0){ "El ID no puede ser menor o igual que 0." }
        require(precio > 0) { "El precio no puede ser menor o igual que 0 €" }
        return dao.modifyProducto(id, precio)
    }
}