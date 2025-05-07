package es.prog2425.ejerciciosBD9_1.service

interface IProductoService {
    fun addProducto(nombre: String, precio: Double, stock: Int)
}