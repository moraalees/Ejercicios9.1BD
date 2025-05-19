package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Producto

/**
 * Interfaz que dicta todos los métodos del DAO de [Producto]
 */
interface IProductoDAO {
    fun insertarCampo(nombre: String, precio: Double, stock: Int)
    fun getAll(): List<Producto>
    fun getById(id: Int): Producto?
    fun deleteByPrecio(id: Int): Boolean
    fun modifyProducto(id: Int, nuevoPrecio: Double): Boolean
}