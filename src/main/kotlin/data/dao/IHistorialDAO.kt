package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Historial

interface IHistorialDAO {
    fun insertar(entry: Historial)
    fun obtenerTodos(): List<Historial>
    fun borrarTodo()
}