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

    override fun getById(id: Int): Producto? {
        val sql = "SELECT * FROM Producto WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    return if (rs.next()){
                        Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"))
                    } else {
                        null
                    }
                }
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

    override fun deleteByPrecio(id: Int): Boolean {
        val sql = "DELETE FROM Producto WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val modificacion = stmt.executeUpdate()
                return modificacion > 0
            }
        }
    }

    override fun modifyProducto(id: Int, nuevoPrecio: Double): Boolean {
        val sql = "UPDATE Producto SET precio = ? WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, nuevoPrecio)
                stmt.setInt(2, id)
                val modificacion = stmt.executeUpdate()
                return modificacion > 0
            }
        }
    }
}