package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.ProductoDAOH2

class ProductoService(private val dao: ProductoDAOH2) : IProductoService {
    override fun addProducto(nombre: String, precio: Double, stock: Int) {
        require(nombre.isNotBlank()){ "El nombre no puede estar vacío." }
        require(precio > 0){ "El precio debe ser mayor que 0." }
        require(stock >= 0){ "El stock debe ser mayor o igual que 0." }
        dao.insertarCampo(nombre.trim(), precio, stock)
    }
}