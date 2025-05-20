package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Historial
import javax.sql.DataSource
import java.sql.Timestamp

class HistorialDAOH2(private val ds: DataSource): IHistorialDAO {

    override fun insertar(entry: Historial) {
        val sql = "INSERT INTO historial (mensaje, fecha_hora) VALUES (?, ?)"
        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.setString(1, entry.mensaje)
            stmt.setTimestamp(2, Timestamp.valueOf(entry.fechaHora))
            stmt.executeUpdate()
        }
    }

    override fun obtenerTodos(): List<Historial> {
        val sql = "SELECT * FROM historial ORDER BY fecha_hora DESC"
        val lista = mutableListOf<Historial>()
        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            val rs = stmt.executeQuery()
            while (rs.next()) {
                lista.add(Historial(rs.getString("mensaje"), rs.getTimestamp("fecha_hora").toLocalDateTime()))
            }
        }
        return lista
    }

    override fun borrarTodo() {
        val sql = "DELETE FROM historial"
        ds.connection.use { conn ->
            val stmt = conn.prepareStatement(sql)
            stmt.executeUpdate()
        }
    }
}