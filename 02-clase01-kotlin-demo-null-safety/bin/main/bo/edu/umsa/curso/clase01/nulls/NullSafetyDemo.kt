package bo.edu.umsa.curso.clase01.nulls

/**
 * Clase 1 — Null safety, scope functions (let/apply/also/run), extensiones.
 */
fun main() {
    println("=== Demo: null safety y scope functions ===\n")

    agendaConDatosSucios()
    safeCallYEncadenamiento()
    elvisYValoresCalculados()
    letVsRun()
    configuracionConApplyAlso()
    reporteDeCorreos()
    filtrarNulosYFirstOrNull()
    takeIfTakeUnless()
}

private fun agendaConDatosSucios() {
    val nombres: List<String?> = listOf("Luis", null, "  ", "María", null, "Pedro")

    println("— Lista con nulos —")
    val validos = nombres.mapNotNull { it?.trim()?.takeIf { s -> s.isNotEmpty() } }
    println("Nombres limpios: $validos")

    val lineas = nombres.map { nombreNullable ->
        nombreNullable?.let { n -> "Contacto: ${n.trim()}" } ?: "(sin nombre)"
    }
    println("Líneas:")
    lineas.forEach { println("  • $it") }
    println()
}

private fun safeCallYEncadenamiento() {
    println("— ?. encadenado —")
    data class Direccion(val ciudad: String?)
    data class Persona(val nombre: String?, val direccion: Direccion?)

    val p1 = Persona("Ana", Direccion("La Paz"))
    val p2 = Persona(null, null)

    fun ciudad(p: Persona?): String? = p?.direccion?.ciudad
    println("  ciudad p1: ${ciudad(p1)}")
    println("  ciudad p2: ${ciudad(p2)}")
    println()
}

private fun elvisYValoresCalculados() {
    println("— Elvis ?: con rama más compleja —")
    val clave: String? = null
    val resuelto = clave ?: run {
        val defecto = "token-${System.currentTimeMillis() % 1000}"
        defecto
    }
    println("  clave resuelta: $resuelto\n")
}

private fun letVsRun() {
    println("— let (it) vs run (this) —")
    val s: String? = "  kotlin "

    val conLet = s?.let { texto -> texto.trim().replaceFirstChar { it.uppercase() } }
    val conRun = s?.run { trim().uppercase() }

    println("  let → $conLet")
    println("  run → $conRun\n")
}

private data class ConfiguracionEnvio(
    var host: String = "localhost",
    var puerto: Int = 8080,
    var usarTls: Boolean = false,
)

private fun configuracionConApplyAlso() {
    val cfg = ConfiguracionEnvio().apply {
        host = "api.umsa.bo"
        puerto = 443
        usarTls = true
    }

    val log = StringBuilder().also { sb ->
        sb.append("[CONFIG] ")
        sb.append("host=${cfg.host}, puerto=${cfg.puerto}, tls=${cfg.usarTls}")
    }

    println("— apply / also —")
    println(log.toString())
    println()
}

private fun reporteDeCorreos() {
    val correos = listOf("ana@umsa.bo", null, "no-es-correo", "  ", "bob@ejemplo.com")

    println("— Validación con extensión —")
    correos.forEach { c ->
        val etiqueta = c.orDash().let { if (it.esEmailLoose()) "OK: $it" else "Inválido: $it" }
        println("  $etiqueta")
    }
    println()
}

private fun filtrarNulosYFirstOrNull() {
    println("— filterNotNull / firstOrNull —")
    val mezcla: List<Int?> = listOf(1, null, 3, null, 5)
    val soloValidos = mezcla.filterNotNull()
    val primeroPar = soloValidos.firstOrNull { it % 2 == 0 }
    println("  lista: $mezcla → sin null: $soloValidos")
    println("  primer par o null: $primeroPar\n")
}

private fun takeIfTakeUnless() {
    println("— takeIf / takeUnless —")
    val edad = 17
    val puedeVotar = edad.takeIf { it >= 18 }?.let { "sí ($it)" } ?: "no ($edad)"
    val esMenor = edad.takeUnless { it >= 18 }?.let { "menor de edad" } ?: "mayor"
    println("  puede votar: $puedeVotar")
    println("  takeUnless: $esMenor\n")
}

/** Elvis para mostrar un marcador cuando el valor es null o en blanco. */
private fun String?.orDash(): String = this?.trim()?.takeIf { it.isNotEmpty() } ?: "—"

/** Validación muy simple para demo en vivo (no es producción). */
private fun String.esEmailLoose(): Boolean = contains("@") && indexOf("@") in 1 until lastIndex
