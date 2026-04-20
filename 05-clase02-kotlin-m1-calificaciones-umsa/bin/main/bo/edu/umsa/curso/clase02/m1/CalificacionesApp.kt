package bo.edu.umsa.curso.clase02.m1

/**
 * Mini-proyecto M1 (Kotlin puro): calificaciones universitarias sin UI.
 * data class, colecciones, sealed class, destructuring, groupBy, etc.
 */
fun main() {
    println("=== M1: Sistema de calificaciones (UMSA) ===\n")

    val estudiantes = listOf(
        Estudiante("001", "Ana López"),
        Estudiante("002", "Luis Quispe"),
        Estudiante("003", "Carmen Rojas"),
    )

    val notas = listOf(
        Nota("001", "Programación I", 85.0),
        Nota("001", "Base de Datos", 72.0),
        Nota("002", "Programación I", 45.0),
        Nota("002", "Base de Datos", 38.0),
        Nota("003", "Programación I", 91.0),
        Nota("003", "Base de Datos", 88.0),
        Nota("003", "Cálculo I", 55.0),
    )

    estudiantes.forEach { e ->
        val resultado = calcularPromedioGeneral(notas, e.id)
        println(formatearReporte(e, resultado))
        detalleNotasPorEstudiante(notas, e)
        println()
    }

    resumenPorMateria(notas)
    rankingPorPromedio(estudiantes, notas)
    conteoAprobados(estudiantes, notas)
    peorYMejorNotaPorEstudiante(estudiantes, notas)
}

private data class Estudiante(
    val id: String,
    val nombreCompleto: String,
)

private data class Nota(
    val estudianteId: String,
    val materia: String,
    val puntaje: Double,
)

private sealed class ResultadoPromedio {
    data class Ok(val promedio: Double, val estado: EstadoAcademico) : ResultadoPromedio()
    data object SinNotas : ResultadoPromedio()
}

private enum class EstadoAcademico { Aprobado, Reprobado }

private fun calcularPromedioGeneral(notas: List<Nota>, estudianteId: String): ResultadoPromedio {
    val delEstudiante = notas.filter { it.estudianteId == estudianteId }
    if (delEstudiante.isEmpty()) return ResultadoPromedio.SinNotas

    val promedio = delEstudiante.map { it.puntaje }.average()
    val estado = if (promedio >= 51.0) EstadoAcademico.Aprobado else EstadoAcademico.Reprobado
    return ResultadoPromedio.Ok(promedio = promedio, estado = estado)
}

private fun formatearReporte(estudiante: Estudiante, resultado: ResultadoPromedio): String = buildString {
    appendLine("Estudiante: ${estudiante.nombreCompleto} (${estudiante.id})")
    when (resultado) {
        is ResultadoPromedio.SinNotas -> appendLine("  Sin notas registradas.")
        is ResultadoPromedio.Ok -> {
            appendLine("  Promedio general: ${"%.2f".format(resultado.promedio)}")
            appendLine("  Estado: ${resultado.estado}")
        }
    }
}

private fun detalleNotasPorEstudiante(notas: List<Nota>, estudiante: Estudiante) {
    val del = notas.filter { it.estudianteId == estudiante.id }
    if (del.isEmpty()) return
    println("  Detalle por materia:")
    del.sortedBy { it.materia }.forEach { (id, materia, puntaje) ->
        println("    • $materia: ${"%.1f".format(puntaje)}")
    }
}

private fun resumenPorMateria(notas: List<Nota>) {
    println("— Resumen por materia (promedio del grupo) —")
    val porMateria = notas.groupBy { it.materia }
    porMateria.toSortedMap().forEach { (materia, lista) ->
        val prom = lista.map { it.puntaje }.average()
        val cantidad = lista.size
        println("  $materia: promedio ${"%.2f".format(prom)} (n=$cantidad)")
    }
    println()
}

private fun rankingPorPromedio(estudiantes: List<Estudiante>, notas: List<Nota>) {
    println("— Ranking por promedio (mayor a menor) —")
    val pares: List<Pair<Estudiante, Double>> = estudiantes.mapNotNull { e ->
        val n = notas.filter { it.estudianteId == e.id }
        if (n.isEmpty()) null else e to n.map { it.puntaje }.average()
    }
    pares
        .sortedByDescending { it.second }
        .forEachIndexed { i, (est, prom) ->
            println("  ${i + 1}. ${est.nombreCompleto}: ${"%.2f".format(prom)}")
        }
    println()
}

private fun conteoAprobados(estudiantes: List<Estudiante>, notas: List<Nota>) {
    println("— Conteo aprobados / reprobados (por promedio) —")
    val resultados = estudiantes.map { e -> calcularPromedioGeneral(notas, e.id) }
    val aprobados = resultados.count { it is ResultadoPromedio.Ok && it.estado == EstadoAcademico.Aprobado }
    val reprobados = resultados.count { it is ResultadoPromedio.Ok && it.estado == EstadoAcademico.Reprobado }
    val sinNotas = resultados.count { it is ResultadoPromedio.SinNotas }
    println("  Aprobados: $aprobados, Reprobados: $reprobados, Sin notas: $sinNotas\n")
}

private fun peorYMejorNotaPorEstudiante(estudiantes: List<Estudiante>, notas: List<Nota>) {
    println("— Mejor y peor nota por estudiante —")
    estudiantes.forEach { e ->
        val del = notas.filter { it.estudianteId == e.id }
        if (del.isEmpty()) {
            println("  ${e.nombreCompleto}: sin notas")
            return@forEach
        }
        val max = del.maxBy { it.puntaje }
        val min = del.minBy { it.puntaje }
        println(
            "  ${e.nombreCompleto}: mejor ${max.materia} (${"%.1f".format(max.puntaje)}), " +
                "peor ${min.materia} (${"%.1f".format(min.puntaje)})",
        )
    }
}
