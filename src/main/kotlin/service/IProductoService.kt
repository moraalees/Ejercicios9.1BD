package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Producto

/**
 * Interfaz que dicta todos los métodos del servicio de [Producto]
 */
interface IProductoService {
    fun addProducto(nombre: String, precio: Double, stock: Int)
    fun obtenerProductos(): List<Producto>
    fun obtenerPorId(id: Int): Producto?
    fun eliminarProducto(id: Int): Boolean
    fun modificarProducto(id: Int, precio: Double): Boolean
}