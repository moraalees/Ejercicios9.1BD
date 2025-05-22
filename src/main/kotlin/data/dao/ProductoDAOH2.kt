package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Producto
import java.sql.SQLException
import javax.sql.DataSource

/**
 * Implementación de [IProductoDAO] que maneja las operaciones en la BD
 *
 * @param ds Fuente de datos utilizada para obtener conexiones a la base de datos.
 */
class ProductoDAOH2(private val ds: DataSource) : IProductoDAO {

    /**
     * Inserta un nuevo [Producto] en la base de datos.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param stock Cantidad disponible en inventario.
     */
    override fun insertarCampo(nombre: String, precio: Double, stock: Int) {
        val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombre)
                    stmt.setDouble(2, precio)
                    stmt.setInt(3, stock)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Recupera un [Producto] por su ID.
     *
     * @param id ID del producto.
     * @return Objeto [Producto] si se encuentra, o `null` si no existe.
     */
    override fun getById(id: Int): Producto? {
        val sql = "SELECT * FROM Producto WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, id)
                    stmt.executeQuery().use { rs ->
                        return if (rs.next()) {
                            Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"))
                        } else {
                            null
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Recupera todos los productos disponibles en la base de datos.
     *
     * @return Lista de objetos [Producto].
     */
    override fun getAll(): List<Producto> {
        val listaProductos = mutableListOf<Producto>()
        val sql = "SELECT * FROM Producto"
        try {
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
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
        return listaProductos
    }

    /**
     * Elimina un [Producto] de la base de datos según su ID.
     *
     * @param id ID del producto que se desea eliminar.
     * @return Boolean: true si se eliminó algún registro o false si no se encontró el producto.
     */
    override fun deleteByPrecio(id: Int): Boolean {
        val sql = "DELETE FROM Producto WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, id)
                    val modificacion = stmt.executeUpdate()
                    return modificacion > 0
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Modifica el precio de un [Producto] identificado por su ID.
     *
     * @param id ID del producto a actualizar.
     * @param nuevoPrecio Nuevo precio a establecer.
     * @return Boolean: true si se modificó algún registro o false si no se encontró el producto.
     */
    override fun modifyProducto(id: Int, nuevoPrecio: Double): Boolean {
        val sql = "UPDATE Producto SET precio = ? WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, nuevoPrecio)
                    stmt.setInt(2, id)
                    val modificacion = stmt.executeUpdate()
                    return modificacion > 0
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}