package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Producto
import java.sql.*

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
        val connection = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión exitosa.")
        if (connection != null){
            var stmt: Statement? = null
            try {
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, nombre)
                stmt.setDouble(2, precio)
                stmt.setInt(3, stock)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al insertar los campos en las tablas: ${e.message}")
            } catch (e: Exception) {
                throw Exception("Error inesperado: ${e.message}")
            } finally {
                stmt?.close()
                try{
                    DatabaseTienda.closeConnection(connection)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
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
        val connection = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión exitosa.")
        if (connection != null){
            var stmt: Statement? = null
            try {
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, producto.nombre)
                stmt.setDouble(2, producto.precio)
                stmt.setInt(3, producto.stock)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al insertar los campos en las tablas")
            } catch (e: Exception) {
                throw Exception("Error al insertar los campos en las tablas")
            } finally {
                stmt?.close()
                try{
                    DatabaseTienda.closeConnection(connection)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Obtiene todos los productos registrados en la base de datos.
     *
     * @return Una lista de objetos [Producto] con la información de cada producto.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     * @throws Exception Para cualquier otro tipo de error inesperado durante la operación.
     */
    override fun getAll(): List<Producto> {
        val conn = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        val listaProductos = mutableListOf<Producto>()
        println("Conexión exitosa.")
        if (conn != null){
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
                try{
                    DatabaseTienda.closeConnection(conn)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
            return listaProductos
        }
        return listaProductos
    }

    /**
     * Elimina de la base de datos todos los productos cuyo precio sea igual al especificado.
     *
     * Este metodo ejecuta  sobre la tabla `Producto` filtrando por el campo `precio` para así eliminar el producto.
     *
     * @param precio Precio de los productos que se desean eliminar.
     * @throws SQLException Si ocurre un error al ejecutar la sentencia SQL.
     * @throws Exception Si ocurre cualquier otro tipo de error.
     */
    override fun deleteByPrecio(precio: Double) {
        val conn = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión exitosa.")
        if (conn != null){
            var stmt: PreparedStatement? = null
            try{
                val sql = "DELETE FROM Producto WHERE precio = ?"
                stmt = conn.prepareStatement(sql)
                stmt.setDouble(1, precio)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al eliminar el producto en la base de datos: ${e.message}")
            } catch (e: Exception) {
                throw Exception("Error: ${e.message}")
            } finally {
                stmt?.close()
                try{
                    DatabaseTienda.closeConnection(conn)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
        }
    }
}