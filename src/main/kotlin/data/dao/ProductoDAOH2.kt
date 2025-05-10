package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Producto
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ProductoDAOH2 : IProductoDAO {
    /**
     * Crea un nuevo producto en la base de datos con los valores individuales proporcionados.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio unitario del producto.
     * @param stock Cantidad disponible en inventario.
     *
     * Establece una conexión a la base de datos, prepara una consulta SQL con parámetros,
     * ejecuta la consulta y seguidamente maneja cualquier error que pueda surgir.
     */
    override fun insertarCampo(nombre: String, precio: Double, stock: Int) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, nombre)
            stmt.setDouble(2, precio)
            stmt.setInt(3, stock)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
            DatabaseTienda.closeConnection(connection)
        }
    }

    /**
     * Crea un nuevo producto en la base de datos usando una clase `Producto`.
     *
     * @param producto Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funciona igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(producto: Producto) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, producto.nombre)
            stmt.setDouble(2, producto.precio)
            stmt.setInt(3, producto.stock)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
            DatabaseTienda.closeConnection(connection)
        }
    }

    override fun getAll(): List<Producto> {
        val conn = DatabaseTienda.getConnection()
        val listaProductos = mutableListOf<Producto>()
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            val sql = "SELECT * FROM Producto"
            stmt = conn.prepareStatement(sql)
            rs = stmt.executeQuery()
            while (rs.next()) {
                val id = rs.getInt("id")
                val nombre = rs.getString("nombre")
                val precio = rs.getDouble("precio")
                val stock = rs.getInt("stock")
                listaProductos.add(Producto(id, nombre, precio, stock))
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtener los productos: ${e.message}")
        } catch(e: Exception) {
            throw Exception("Error: ${e.message}")
        }finally {
            rs?.close()
            stmt?.close()
            DatabaseTienda.closeConnection(conn)
        }
        return listaProductos
    }
}