package es.prog2425.ejerciciosBD9_1

import es.prog2425.ejerciciosBD9_1.app.InicializadorTablas
import es.prog2425.ejerciciosBD9_1.data.dao.UsuarioDAOH2
import es.prog2425.ejerciciosBD9_1.service.UsuarioService

fun main() {
    InicializadorTablas.crearTablas()

    val dao = UsuarioDAOH2()
    val service = UsuarioService(dao)

    service.addUsuario("Facundo Pérez", "facuper@mail.com")
    service.addUsuario("Ataulfo Rodríguez", "ataurod@mail.com")
    service.addUsuario("Cornelio Ramírez", "Cornram@mail.com")
}