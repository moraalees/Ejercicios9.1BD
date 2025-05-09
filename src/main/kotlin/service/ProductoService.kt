package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2
import es.prog2425.ejerciciosBD9_1.model.Producto

class ProductoService(private val dao: ProductoDAOH2) : IProductoService {
    override fun addProducto(nombre: String, precio: Double, stock: Int) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        require(stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(nombre.trim(), precio, stock)
    }

    override fun addProducto(producto: Producto) {
        require(producto.nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(producto.precio > 0){ "El precio debe ser mayor que 0." }
        require(producto.stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(producto.nombre.trim(), producto.precio, producto.stock)
    }
}