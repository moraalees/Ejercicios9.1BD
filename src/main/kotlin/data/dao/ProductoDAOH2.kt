package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Producto
import javax.sql.DataSource

class ProductoDAOH2(private val ds: DataSource) : IProductoDAO {

    override fun insertarCampo(nombre: String, precio: Double, stock: Int) {
        val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.setDouble(2, precio)
                stmt.setInt(3, stock)
                stmt.executeUpdate()
            }
        }
    }

    override fun insertarCampo(producto: Producto) {
        val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, producto.nombre)
                stmt.setDouble(2, producto.precio)
                stmt.setInt(3, producto.stock)
                stmt.executeUpdate()
            }
        }
    }

    override fun getAll(): List<Producto> {
        val listaProductos = mutableListOf<Producto>()
        val sql = "SELECT * FROM Producto"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val nombre = rs.getString("nombre")
                        val precio = rs.getDouble("precio")
                        val stock = rs.getInt("stock")
                        listaProductos.add(Producto(id, nombre, precio, stock))
                    }
                }
            }
        }
        return listaProductos
    }

    override fun deleteByPrecio(precio: Double) {
        val sql = "DELETE FROM Producto WHERE precio = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, precio)
                stmt.executeUpdate()
            }
        }
    }

    override fun modifyProducto(nombre: String, nuevoPrecio: Double) {
        val sql = "UPDATE Producto SET precio = ? WHERE nombre = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, nuevoPrecio)
                stmt.setString(2, nombre)
                stmt.executeUpdate()
            }
        }
    }
}