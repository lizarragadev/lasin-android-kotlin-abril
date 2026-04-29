package bo.edu.umsa.curso.clase01.nulls

fun main() {
    println("02 null safety — base")

    agendaConDatosSucios()
    safeCallYEncadenamiento()
    elvisYValoresCalculados()
    letVsRun()
    filtrarNulosYFirstORNull()
    takeIfTakeUnless()
}

fun takeIfTakeUnless() {
    println("- TakeIf / TakeUnless -")
    val edad = 19
    val puedeVotar = edad.takeIf { it >= 18 }?.let {
        "Si $it"
    } ?: "No $edad"

    val esMenor = edad.takeUnless { it >= 18 }?.let {
        "menor de edad"
    } ?: "mayor de edad"

    println("Puede votar: $puedeVotar")
    println("Takeunless $esMenor")

}

fun filtrarNulosYFirstORNull() {
    println("- filterNotNull / firstOrNull -")
    val mezcla: List<Int?> = listOf(1, null, 3, null, 5)
    val soloValidos = mezcla.filterNotNull()

    val primeroPar = soloValidos.firstOrNull { it % 2 == 0}

    println("Lista: $mezcla -> sin null: $soloValidos")
    println("Primer par o null: $primeroPar")
}

fun letVsRun() {
    // Persona(nombre: String, edad: Int)
    // persona.nombre = "N"
    // persona.edad = 34

    println("- Let vs Run -")
    val s: String? = " kotlin"
    println("Valor original: $s")
    // let recibe el objeto como parámetro
    val conLet = s?.let { texto ->
        texto.trim().replaceFirstChar { it.uppercase() }
    }
    println("Valor modificado con LET: $conLet")

    val conRun = s?.run {
        trim().replaceFirstChar { it.uppercase() }
    }
    println("Valor modificado con RUN: $conRun")

}

fun elvisYValoresCalculados() {
    //  ?:   operador Elvis
    println("- ?: Elvis -")
    val clave: String? = "FFFFFF&&&&"
    val resuelto = clave ?: "JJFJ&&*&)*(564664"
    println(" Clave resuelta: $resuelto")
}

fun safeCallYEncadenamiento() {
    //  ?.  safe call
    println("- ?. encadenado -")
    data class Direccion(val ciudad: String?)
    data class Persona(val nombre: String?, val direccion: Direccion?)

    val p1 = Persona("Luisa", Direccion("La Paz"))
    val p2 = Persona(null, null)

    fun ciudad(p: Persona?): String? = p?.direccion?.ciudad

    println("Ciudad p1: ${ciudad(p1)}")
    println("Ciudad p2: ${ciudad(p2)}")
}

fun agendaConDatosSucios() {
    // String  -> el valor no puede ser nulo
    // String?, Int?, Persona?  -> el valor si puede ser nulo
    val nombres: List<String?> = listOf("Luis", null, " ", "Maria", null, "Pedro")
    println("- Lista con Nulos -")
    val validos = nombres.mapNotNull { it?.trim()?.takeIf { s -> s.isNotEmpty() } }
    println("Nombres limpios: $validos")

    val lineas = nombres.map { nombreNullable ->
        nombreNullable?.let { n ->
            "Contacto: ${n.trim()}"
        } ?: "(sin nombre)"
    }
    println("Lineas:")
    lineas.forEach { println(" - $it") }

}
