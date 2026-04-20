package bo.edu.umsa.curso.clase01.fundamentos

/**
 * Clase 1 — Fundamentos: variables, condicionales, rangos, bucles, templates, funciones.
 * Ejecutar: ./gradlew run (o Run en IntelliJ).
 */
fun main() {
    println("=== Demo: fundamentos de Kotlin ===\n")

    variablesYTipos()
    conversionesYLiterales()
    condicionalesIfComoExpresion()
    whenConSujetoYVariasRam()
    whenExhaustivoYTipos()
    rangosYBucles()
    whileYDoWhile()
    tablaDeMultiplicar(factor = 7, hasta = 10)
    calculadoraDeCategoria(anioNacimiento = 2000)
    funcionesConParametrosPorDefectoYNombrados()
    funcionesLocalesYUnaLinea()
    textoMultilinea()
}

private fun variablesYTipos() {
    val nombre = "Ana"
    var edad = 20
    val alturaMetros = 1.68
    val esEstudiante = true
    val inicial: Char = 'A'

    println("— Variables —")
    println("Hola $nombre, tienes ${edad + 1} años el próximo cumpleaños.")
    println("Altura: $alturaMetros m, inicial: $inicial, estudiante: $esEstudiante")
    edad = 21
    println("Edad actualizada: $edad\n")
}

private fun conversionesYLiterales() {
    println("— Números y literales —")
    val binario = 0b1010
    val hex = 0xFF
    val millones = 1_000_000
    println("  binario 0b1010 = $binario, hex 0xFF = $hex, separador _ : $millones")

    val x = 10
    val y: Long = x.toLong()
    val z = "123".toInt()
    println("  Int→Long: $y, String→Int: $z\n")
}

private fun condicionalesIfComoExpresion() {
    val nota = 78

    val estado = if (nota >= 51) {
        "Aprobado"
    } else {
        "Reprobado"
    }

    val literal = when (nota) {
        in 90..100 -> "Excelente"
        in 70..89 -> "Muy bien"
        in 51..69 -> "Suficiente"
        else -> "Insuficiente"
    }

    println("— Condicionales (if como expresión) —")
    println("Nota: $nota → $estado ($literal)\n")
}

private fun whenConSujetoYVariasRam() {
    val dia = "MIÉ"
    val tipo = when (dia) {
        "LUN", "MAR", "MIÉ", "JUE", "VIE" -> "día de clase"
        "SÁB", "DOM" -> "fin de semana"
        else -> "desconocido"
    }
    println("— when con varias ramas — $dia → $tipo\n")
}

private fun whenExhaustivoYTipos() {
    println("— when con tipos mezclados (Any) —")
    fun describir(valor: Any): String = when (valor) {
        is String -> "texto (${valor.length} caracteres)"
        is Int -> "entero $valor"
        is Double -> "decimal $valor"
        else -> "otro: ${valor::class.simpleName}"
    }
    println("  ${describir("UMSA")}")
    println("  ${describir(42)}")
    println("  ${describir(3.14)}")
    println("  ${describir(listOf(1))}\n")
}

private fun rangosYBucles() {
    println("— Rangos y bucles —")
    print("for 1..5: ")
    for (i in 1..5) print("$i ")
    println()

    print("until (excluye fin) 1 until 5: ")
    for (i in 1 until 5) print("$i ")
    println()

    print("downTo 5 a 1 step 2: ")
    for (i in 5 downTo 1 step 2) print("$i ")
    println()

    print("repeat(3): ")
    repeat(3) { print("tic ") }
    println("\n")
}

private fun whileYDoWhile() {
    println("— while / do-while —")
    var n = 3
    print("  cuenta atrás while: ")
    while (n > 0) {
        print("$n ")
        n--
    }
    println()

    var m = 0
    print("  do-while al menos una vez: ")
    do {
        print("$m ")
        m++
    } while (m < 3)
    println("\n")
}

private fun tablaDeMultiplicar(factor: Int, hasta: Int) {
    println("— Tabla del $factor —")
    for (n in 1..hasta) {
        println("$factor × $n = ${factor * n}")
    }
    println()
}

private fun calculadoraDeCategoria(anioNacimiento: Int) {
    val anioActual = 2026
    val edad = anioActual - anioNacimiento

    val categoria = when {
        edad < 13 -> "Infantil"
        edad < 18 -> "Adolescente"
        edad < 65 -> "Adulto"
        else -> "Adulto mayor"
    }

    println("— Categoría por edad —")
    println("Nacimiento: $anioNacimiento → edad aproximada: $edad → categoría: $categoria\n")
}

private fun funcionesConParametrosPorDefectoYNombrados() {
    println("— Parámetros por defecto y nombrados —")

    fun saludar(nombre: String, formal: Boolean = false, veces: Int = 1) {
        val texto = if (formal) "Estimado/a $nombre" else "Hola $nombre"
        repeat(veces) { println("  $texto") }
    }

    saludar("Luis")
    saludar(nombre = "Ana", formal = true)
    saludar("Equipo", veces = 2, formal = false)
    println()
}

private fun funcionesLocalesYUnaLinea() {
    println("— Función local y de una sola expresión —")

    fun cuadrado(n: Int) = n * n

    fun procesar(nums: List<Int>): Int {
        fun sumarPares(lista: List<Int>) = lista.filter { it % 2 == 0 }.sum()
        return sumarPares(nums.map { cuadrado(it) })
    }

    println("  cuadrado(5)=${cuadrado(5)}")
    println("  procesar([1,2,3]) suma de cuadrados pares = ${procesar(listOf(1, 2, 3))}\n")
}

private fun textoMultilinea() {
    println("— String multilínea (trimIndent) —")
    val bloque = """
        Curso: Android + Kotlin
        Institución: UMSA
        Módulo: fundamentos
    """.trimIndent()
    println(bloque)
}
