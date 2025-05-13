package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Producto

interface IProductoDAO {
    fun insertarCampo(nombre: String, precio: Double, stock: Int)
    fun insertarCampo(producto: Producto)
    fun getAll(): List<Producto>
    fun deleteByPrecio(precio: Double)
    fun modifyProducto(nombre: String, nuevoPrecio: Double)
}