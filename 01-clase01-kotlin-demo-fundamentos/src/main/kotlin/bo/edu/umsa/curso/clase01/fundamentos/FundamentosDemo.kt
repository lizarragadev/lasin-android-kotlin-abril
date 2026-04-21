package bo.edu.umsa.curso.clase01.fundamentos

fun main() {
    variablesYTipos()
    conversionesYLiterales()
    condicionalesIfComoExpresion()
    whenConSujetoYVariasRam()
    whenExhaustivoYTipos()
    rangosYBucles()
    whileYDoWhile()
    tablaDeMultiplicar(7, 10)
    calculadoraDeCategoria(1991)
}

fun calculadoraDeCategoria(anioNacimiento: Int) {
    val anioActual = 2026
    val edad = anioActual - anioNacimiento

    val categoria = when {
        edad < 13 -> "Infantil"
        edad < 18 -> "Adolescente"
        edad < 65 -> "Adulto"
        else -> "Adulto mayor"
    }

    println("- Categoria por Edad -")
    println("Edad: $edad, Categoría: $categoria")
}

fun tablaDeMultiplicar(factor: Int, hasta: Int) {
    println("- Tabla de Multiplicar -")
    for(n in 1..hasta) {
        println("$factor x $n = ${factor * n}")
    }
    println()
}

fun whileYDoWhile() {
    println("- while y do while -")
    var n = 3
    print("cuenta atrás while: ")
    println()
    while(n > 0 ) {
        print("$n ")
        n--
    }
    println()

    var m = 0
    print("cuenta adelante do while: ")
    println()
    do {
        print("$m ")
        m++
    } while(m < 3)
}

fun rangosYBucles() {
    println("- Rangos y Bucles -")
    println("for 1..5")
    for(i in 1..5) {
        print("$i ")
    }
    println()
    for(i in 1 until 5) print("$i ")
    println()
    for(i in 5 downTo 1) print("$i ")
    println()
    for(i in 2..10 step 2) print("$i ")
    println()

    repeat(3) { print("tic ") }
}

private fun whenExhaustivoYTipos() {
    println("- When con tipos mezclados (Any) -")
    fun describir(valor: Any): String = when(valor){
        is String -> "texto (${valor.length} caracteres)"
        is Int -> "entero $valor"
        is Double -> "decimal $valor"
        else -> "Otro: ${valor::class.simpleName}"
    }
    println("${describir("UMSA")}")
    println("${describir(42)}")
    println("${describir(3.14)}")

}

fun whenConSujetoYVariasRam() {
    val dia = "MIÉ"
    val tipo = when (dia) {
        "LUN", "MAR", "MIÉ", "JUE", "VIE" -> "Día de clases"
        "SAB", "DOM" -> "Fin de semana"
        else -> "Desconocido"
    }
    println("- when con varias ramas $dia -> $tipo -")
}

fun condicionalesIfComoExpresion() {
    val nota = 78
    var estado = if(nota >= 51) {
        "Aprobado"
    } else {
        "Reprobado"
    }
    println("Estado alumno: $estado")

    val literal = when(nota) {
        in 90..100 -> "Excelente"
        in 70..89 -> "Muy bien"
        in 51..69 -> "Suficiente"
        else -> "Insuficiente"
    } //    1..10, a..z, A..Z

    println("- Condicionales (if como expresión) -")
    println("Nota: $nota, Literal: $literal")
}

fun conversionesYLiterales() {
    println("- Números y literales -")
    val binario = 0b1010
    val hex = 0xFFFFFF
    val millones = 1_000_000

    println("binario $binario, hex $hex, millones $millones")

    val x = 10
    val y: Long = x.toLong()
    val z = "123".toInt()
    println("x $x, y $y, z $z")
}

fun variablesYTipos() {
    val nombre = "Gustavo" // Variable inmutable
    var edad = 34           // Variable mutable
    val alturaMetros = 1.70
    var esEstudiante = true
    val inicial: Char = 'G'

    println("- Variables -")
    println("Hola $nombre, tendrás ${edad+1} años el próximo cumpleaños")
    println("Altura: $alturaMetros m, inicial: $inicial, estudiante: $esEstudiante")
    edad++
    println("Edad actualizada: $edad")
}


