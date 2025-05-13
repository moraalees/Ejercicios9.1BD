package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Producto

interface IProductoService {
    fun addProducto(nombre: String, precio: Double, stock: Int)
    fun addProducto(producto: Producto)
    fun obtenerProductos(): List<Producto>
    fun eliminarProducto(precio: Double)
    fun modificarProducto(nombre: String, precio: Double)
}