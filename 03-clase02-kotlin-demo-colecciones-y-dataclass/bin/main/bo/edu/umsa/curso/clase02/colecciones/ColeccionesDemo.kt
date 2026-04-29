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
    anyAllNone(cursos)
    flatMap()
    partitionDemo(cursos)
    mapVsMutableMap()

    ejemploClases()
    ejemploDataClass()
    ejemploHerencia()
    ejemploInterfaces()
    ejemploSealedClass()
}

class Persona(
    val nombre: String,
    val edad: Int
) {
     // no es necesario getters, setters, constructores

    fun presentarse() {
        println("Hola, soy $nombre y tengo $edad años.")
    }
}

private fun ejemploClases() {
    val persona = Persona("Luis", 24)
    persona.presentarse()
}

data class Cursoo(
    val nombre: String,
    val creditos: Int
)

fun ejemploDataClass() {
    val curso1 = Cursoo("Programacion II", 5)
    val curso2 = curso1.copy(creditos = 10)
    println(curso1)
    println(curso2)
}

open class Animal(val nombre: String) {
    fun respirar(){
        println("$nombre respirando...")
    }
}

class Perro(nombre: String): Animal(nombre) {
    fun ladrar() {
        println("$nombre ladrando...")
    }
}

fun ejemploHerencia() {
    val perro = Perro("Firulais")

    perro.respirar()
    perro.ladrar()
}

interface Volador {
    fun volar()
}

class Pajaro: Volador {
    override fun volar() {
        println("El pájaro está volando")
    }
}

class Avion: Volador {
    override fun volar() {
        println("El avión esta planeando")
    }
}

fun ejemploInterfaces() {
    val pajaro = Pajaro()
    val avion = Avion()
    pajaro.volar()
    avion.volar()
}

sealed class Resultado {
    data class Exito(val datos: String): Resultado()
    data class Error(val mensaje: String): Resultado()
    object Cargando: Resultado()
}

fun ejemploSealedClass() {
    val resultado: Resultado = Resultado.Exito("Datos cargados.")
    val resultado2: Resultado = Resultado.Error("Error en la API")

    when(resultado) {
        is Resultado.Exito -> println("Éxito: ${resultado.datos}")
        is Resultado.Error -> println("Error: ${resultado.mensaje}")
        is Resultado.Cargando -> println("Cargando...")
    }
}

fun mapVsMutableMap() {
    println("- map vs mutableMap -")
    // mapOf, mutableMapOf
    val ro = mapOf("a" to 1, "b" to 2)
    val rw = mutableMapOf("x" to 10)

    println("Inmutable: $ro")
    println("Mutable: $rw")
    rw["y"] = 20
    rw["x"] = 5
    println("Mutable editado: $rw")
}

fun partitionDemo(cursos: List<Curso>) {
    println("- partition -")
    val (inf, resto) = cursos.partition { it.carrera == "INF" }
    println("Solo INF: $inf")
    println("Resto: $resto")
}

fun flatMap() {
    println("- flatMap -")
    val porCarrera: Map<String, List<String>> = mapOf(
        "INF" to listOf("Prog I", "BD"),
        "MAT" to listOf("Cálculo", "Álgebra")
    )
    val todasLasMaterias = porCarrera.flatMap { it.value }
    println("Materias Aplanadas: $todasLasMaterias")
}

fun anyAllNone(cursos: List<Curso>) {
    println("- any (al menos uno cumple?), all (todos cumplen?), none(ninguno cumple?) -")
    cursos.forEach { println(it) }
    println("----")
    val hayInf = cursos.any { it.carrera == "INF" }
    val todosMasDe3 = cursos.all { it.creditos > 3 }
    val ningunoQuim = cursos.none { it.carrera == "QUI" }
    println("Hay INF? $hayInf")
    println("Todos mas de 3? $todosMasDe3")
    println("Ninguno de QUIM? $ningunoQuim")

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


