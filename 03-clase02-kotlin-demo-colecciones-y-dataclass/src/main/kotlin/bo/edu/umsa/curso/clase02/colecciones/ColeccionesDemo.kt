package bo.edu.umsa.curso.clase02.colecciones

data class Curso(
    val nombre: String,
    val carrera: String,
    val creditos: Int
)

fun main() {
    println("- Data class, funciones de orden superior -")

    val cursos = listOf(
        Curso("Programacion I", "INF", 4),
        Curso("Base de Datos", "INF", 5),
        Curso("Calculo I", "MAT", 2),
        Curso("Estructuras", "INF", 6),
        Curso("Algebra", "MAT", 1),
    )
    listasBasicasYDataClassCopy(cursos)
    filterMapGroupBy(cursos)
    sortedYDisctinct(cursos)
}

fun sortedYDisctinct(cursos: List<Curso>) {
    println("- sortedBy, distinctBy -")
    cursos.forEach { println(it) }
    println("----")
    val porNombre = cursos.sortedBy { it.nombre }
    porNombre.forEach { println(it) }
    println("----")
    val conDup = cursos + cursos.first()
    cursos.forEach { println(it) }
    println("********")
    conDup.forEach { println(it) }
    val unicosPorNombre = conDup.distinctBy { it.nombre }
    println("********")
    unicosPorNombre.forEach { println(it) }
}

fun filterMapGroupBy(cursos: List<Curso>) {
    // filter, map, groupBy
    println("- filter, map, groupBy -")
    cursos.forEach { println(it) }
    println("----")
    val soloInf = cursos.filter { it.carrera == "INF" }
    println("Solo INF: $soloInf")
    println("----")
    val nombres = cursos.map { it.nombre.uppercase() }
    println("Nombres en mayus: $nombres")
    println("----")
    val porCarrera = cursos.groupBy { it.carrera }
    println("Por carrera: $porCarrera")
    println("----")
    val creditosInf = cursos
        .filter { it.carrera == "INF" }
        .sumOf { it.creditos }
    println("Creditos INF: $creditosInf")
}

fun listasBasicasYDataClassCopy(cursos: List<Curso>) {
    println("- data class: equals, copy -")
    val a = cursos.first()
    val b = a.copy()
    println(" a == b ? ${a == b}")
    val c = a.copy(creditos = 8)
    println("Original: $a")
    println("Con copy modif: $c")
}


