package bo.edu.umsa.curso.clase02.colecciones

/**
 * Clase 2 — data class, colecciones inmutables/mutables, map/filter/reduce, etc.
 */
fun main() {
    println("=== Demo: data class y funciones de orden superior ===\n")

    val cursos = listOf(
        Curso("Programación I", "INF", 4),
        Curso("Base de Datos", "INF", 5),
        Curso("Cálculo I", "MAT", 6),
        Curso("Estructuras", "INF", 5),
        Curso("Álgebra", "MAT", 6),
    )

    listasBasicasYDataClassCopy(cursos)
    filterMapGroupBy(cursos)
    sortedYDistinct(cursos)
    anyAllNone(cursos)
    flatMapEjemplo()
    associateYAssociateBy(cursos)
    partition(cursos)
    foldYReduce()
    mapVsMutableMap()
}

private data class Curso(
    val nombre: String,
    val carrera: String,
    val creditos: Int,
)

private fun listasBasicasYDataClassCopy(cursos: List<Curso>) {
    println("— data class: equals, copy —")
    val a = cursos.first()
    val b = a.copy()
    println("  a == b (mismos campos, instancias distintas): ${a == b}")
    val c = a.copy(creditos = 5)
    println("  original: $a")
    println("  con copy(creditos=5): $c\n")
}

private fun filterMapGroupBy(cursos: List<Curso>) {
    println("— filter / map / groupBy —")
    cursos.forEach { println("  $it") }

    val soloInf = cursos.filter { it.carrera == "INF" }
    println("\nSolo INF: ${soloInf.joinToString { it.nombre }}")

    val nombres = cursos.map { it.nombre.uppercase() }
    println("Nombres en mayúsculas: $nombres")

    val porCarrera = cursos.groupBy { it.carrera }
    println("\nAgrupados por carrera:")
    porCarrera.forEach { (carrera, lista) ->
        println("  $carrera: ${lista.joinToString { it.nombre }}")
    }

    val creditosInf = cursos.filter { it.carrera == "INF" }.sumOf { it.creditos }
    println("\nCréditos totales INF: $creditosInf\n")
}

private fun sortedYDistinct(cursos: List<Curso>) {
    println("— sortedBy / distinctBy —")
    val porNombre = cursos.sortedBy { it.nombre }
    println("  por nombre: ${porNombre.map { it.nombre }}")

    val conDup = cursos + cursos.first()
    val unicosPorNombre = conDup.distinctBy { it.nombre }
    println("  duplicados eliminados por nombre: ${unicosPorNombre.size} ítems\n")
}

private fun anyAllNone(cursos: List<Curso>) {
    println("— any / all / none —")
    val hayInf = cursos.any { it.carrera == "INF" }
    val todosMasDe3 = cursos.all { it.creditos > 3 }
    val ningunoQuim = cursos.none { it.carrera == "QUI" }
    println("  ¿hay INF? $hayInf")
    println("  ¿todos >3 créditos? $todosMasDe3")
    println("  ¿ninguna carrera QUI? $ningunoQuim\n")
}

private fun flatMapEjemplo() {
    println("— flatMap (aplanar listas de listas) —")
    val porCarrera: Map<String, List<String>> = mapOf(
        "INF" to listOf("Prog I", "BD"),
        "MAT" to listOf("Cálculo", "Álgebra"),
    )
    val todasLasMaterias = porCarrera.flatMap { it.value }
    println("  materias aplanadas: $todasLasMaterias\n")
}

private fun associateYAssociateBy(cursos: List<Curso>) {
    println("— associate / associateBy —")
    val nombreACreditos = cursos.associate { it.nombre to it.creditos }
    println("  map nombre→créditos: $nombreACreditos")

    val porNombre = cursos.associateBy { it.nombre }
    println("  Curso de 'Álgebra': ${porNombre["Álgebra"]}\n")
}

private fun partition(cursos: List<Curso>) {
    println("— partition —")
    val (inf, resto) = cursos.partition { it.carrera == "INF" }
    println("  INF: ${inf.map { it.nombre }}")
    println("  resto: ${resto.map { it.nombre }}\n")
}

private fun foldYReduce() {
    println("— fold / reduce —")
    val nums = listOf(2, 3, 4)
    val producto = nums.fold(1) { acc, n -> acc * n }
    val suma = nums.reduce { a, b -> a + b }
    println("  nums=$nums → producto (fold)=$producto, suma (reduce)=$suma\n")
}

private fun mapVsMutableMap() {
    println("— mapOf vs mutableMapOf —")
    val ro = mapOf("a" to 1, "b" to 2)
    val rw = mutableMapOf("x" to 10)
    rw["y"] = 20
    println("  inmutable: $ro")
    println("  mutable tras añadir: $rw")
}
