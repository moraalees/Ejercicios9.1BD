package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class LineaPedidoDAOH2 : ILineaPedidoDAO{
    /**
     * Crea una nueva línea de pedido en la base de datos con los valores individuales proporcionados.
     *
     * @param idPedido ID del pedido al que pertenece esta línea.
     * @param idProducto ID del producto que se está añadiendo al pedido.
     * @param cantidad Número de unidades del producto.
     * @param precio Precio unitario del producto en ese momento.
     *
     * Establece una conexión a la base de datos, prepara una consulta SQL con parámetros,
     * ejecuta la consulta y seguidamente maneja cualquier error que pueda surgir..
     */
    override fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setInt(1, idPedido)
            stmt.setInt(2, idProducto)
            stmt.setInt(3, cantidad)
            stmt.setDouble(4, precio)
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
     * Crea una nueva línea de pedido en la base de datos usando una clase `LineaPedido`.
     *
     * @param lineaPedido Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funcionalmente igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(lineaPedido: LineaPedido) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setInt(1, lineaPedido.idPedido)
            stmt.setInt(2, lineaPedido.idProducto)
            stmt.setInt(3, lineaPedido.cantidad)
            stmt.setDouble(4, lineaPedido.precio)
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

    override fun insertarCampo(
        conn: Connection,
        idPedido: Int,
        idProducto: Int,
        cantidad: Int,
        precio: Double
    ) {
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, idPedido)
            stmt.setInt(2, idProducto)
            stmt.setInt(3, cantidad)
            stmt.setDouble(4, precio)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
        }
    }

    override fun insertarCampo(conn: Connection, lineaPedido: LineaPedido) {
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, lineaPedido.idPedido)
            stmt.setInt(2, lineaPedido.idProducto)
            stmt.setInt(3, lineaPedido.cantidad)
            stmt.setDouble(4, lineaPedido.precio)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
        }
    }
}