package bo.edu.umsa.curso.clase02.sealed

/**
 * Clase 2 — sealed class: estados de UI, resultados de operación, jerarquías finitas.
 */
fun main() {
    println("=== Demo: sealed classes ===\n")

    estadosUiEnLista()
    simulacionDeCarga()
    resultadosDeApi()
    permisosComoSealed()
    eventosDeUsuario()
    sealedConDatosAnidados()
}

private fun estadosUiEnLista() {
    println("— UiState: lista de muestras —")
    val muestras = listOf(
        UiState.Idle,
        UiState.Loading,
        UiState.Success("Datos listos"),
        UiState.Error("Sin conexión"),
        UiState.Empty,
    )
    muestras.forEach { println(describirUi(it)) }
    println()
}

private fun simulacionDeCarga() {
    println("— Simulación por pasos —")
    fun simular(paso: Int): UiState = when (paso) {
        0 -> UiState.Idle
        1 -> UiState.Loading
        2 -> UiState.Success(listOf("a", "b", "c"))
        else -> UiState.Error("Paso inválido")
    }
    for (i in 0..3) {
        println(describirUi(simular(i)))
    }
    println()
}

private sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class Success(val payload: Any) : UiState()
    data class Error(val mensaje: String) : UiState()
    data object Empty : UiState()
}

private fun describirUi(estado: UiState): String = when (estado) {
    is UiState.Idle -> "Estado: en espera"
    is UiState.Loading -> "Estado: cargando…"
    is UiState.Success -> "Estado: éxito → ${estado.payload}"
    is UiState.Error -> "Estado: error → ${estado.mensaje}"
    is UiState.Empty -> "Estado: sin datos"
}

private fun resultadosDeApi() {
    println("— Resultado<T> genérico —")
    fun <T> combinar(r: Resultado<T>): String = when (r) {
        is Resultado.Exito -> "OK: ${r.dato}"
        is Resultado.ErrorRed -> "Red: ${r.mensaje}"
        is Resultado.ErrorHttp -> "HTTP ${r.codigo}: ${r.cuerpo}"
    }

    println("  ${combinar(Resultado.Exito("usuarios"))}")
    println("  ${combinar(Resultado.ErrorRed("timeout"))}")
    println("  ${combinar(Resultado.ErrorHttp(404, "not found"))}\n")
}

private sealed class Resultado<out T> {
    data class Exito<T>(val dato: T) : Resultado<T>()
    data class ErrorRed(val mensaje: String) : Resultado<Nothing>()
    data class ErrorHttp(val codigo: Int, val cuerpo: String) : Resultado<Nothing>()
}

private fun permisosComoSealed() {
    println("— Jerarquía simple (rol) —")
    fun puedeEditar(rol: Rol): Boolean = when (rol) {
        is Rol.Admin -> true
        is Rol.Docente -> rol.materias.isNotEmpty()
        is Rol.Estudiante -> false
    }

    val r1: Rol = Rol.Admin
    val r2: Rol = Rol.Docente(listOf("INF-101"))
    val r3: Rol = Rol.Estudiante("001")
    println("  admin edita: ${puedeEditar(r1)}")
    println("  docente edita: ${puedeEditar(r2)}")
    println("  estudiante edita: ${puedeEditar(r3)}\n")
}

private sealed class Rol {
    data object Admin : Rol()
    data class Docente(val materias: List<String>) : Rol()
    data class Estudiante(val matricula: String) : Rol()
}

private fun eventosDeUsuario() {
    println("— Eventos de UI (sealed) —")
    fun reaccionar(e: EventoUi): String = when (e) {
        is EventoUi.ClickBoton -> "click en ${e.id}"
        is EventoUi.CambioTexto -> "texto en ${e.campo}: ${e.valor}"
        EventoUi.SolicitarCierre -> "cerrar"
    }

    println("  ${reaccionar(EventoUi.ClickBoton("guardar"))}")
    println("  ${reaccionar(EventoUi.CambioTexto("email", "a@b.co"))}")
    println("  ${reaccionar(EventoUi.SolicitarCierre)}\n")
}

private sealed class EventoUi {
    data class ClickBoton(val id: String) : EventoUi()
    data class CambioTexto(val campo: String, val valor: String) : EventoUi()
    data object SolicitarCierre : EventoUi()
}

private fun sealedConDatosAnidados() {
    println("— sealed anidado (autenticación) —")
    fun mensaje(s: Sesion): String = when (s) {
        is Sesion.Invitado -> "invitado"
        is Sesion.Registrado -> "hola ${s.usuario.nombre} (${s.usuario.rol})"
    }

    val u = Usuario("Ana", RolAdmin)
    println("  ${mensaje(Sesion.Registrado(u))}")
    println("  ${mensaje(Sesion.Invitado)}\n")
}

private sealed class Sesion {
    data object Invitado : Sesion()
    data class Registrado(val usuario: Usuario) : Sesion()
}

private data class Usuario(val nombre: String, val rol: RolSistema)

private sealed class RolSistema {
    data object Estudiante : RolSistema()
    data object Docente : RolSistema()
    data object Admin : RolSistema()
}

private val RolAdmin: RolSistema = RolSistema.Admin
