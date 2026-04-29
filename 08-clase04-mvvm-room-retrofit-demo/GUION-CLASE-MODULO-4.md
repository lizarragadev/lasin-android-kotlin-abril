# Guion de Clase - Modulo 4 (MVVM + Room + Retrofit)

Este guion esta pensado para dictar en vivo partiendo de un proyecto vacio, escribiendo codigo con los alumnos.

Duracion objetivo: 3 horas (180 min)
Nivel: intermedio, entendible, sin exceso de codigo
App objetivo: Notas simples con persistencia local y sincronizacion remota

---

## 0) Resultado final esperado

Al terminar, la app debe permitir:
- Agregar notas locales
- Marcar nota como completada/reabrir
- Filtrar por "solo pendientes"
- Buscar por texto (con debounce)
- Sincronizar tareas remotas con boton "Sync API"
- Ver errores de red de forma amigable

Arquitectura final:
- UI (Compose)
- ViewModel (StateFlow + eventos)
- Repository (orquesta local/remoto)
- Data Sources:
  - Room (local)
  - Retrofit + OkHttp + JSON (remoto)

---

## 1) Requisitos previos (5-10 min)

- Android Studio actualizado
- JDK 17 o superior instalado
- Emulador o dispositivo Android listo
- Internet (para descargar dependencias y consumir API)

Servicio externo usado:
- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) (mock API publica)

---

## 2) Crear proyecto base vacio (10 min)

1. Crear proyecto nuevo:
   - Template: Empty Activity (Compose)
   - Nombre sugerido: `08-clase04-mvvm-room-retrofit-demo`
   - Package sugerido: `bo.edu.umsa.curso.clase04.mvvm`
   - Min SDK: 26

2. Verificar que compile en blanco una vez.

3. Revisar archivos base generados:
   - `settings.gradle.kts`
   - `build.gradle.kts` (raiz)
   - `app/build.gradle.kts`
   - `gradle/libs.versions.toml`
   - `MainActivity.kt`

Checkpoint: proyecto vacio corre.

---

## 3) Dependencias y Gradle (15-20 min)

Objetivo: preparar Compose + Lifecycle + Room + Retrofit + Serialization + KSP.

### 3.1 Editar `gradle/libs.versions.toml`

Agregar (o verificar) versiones y libs:
- Lifecycle Compose
- Coroutines
- Room runtime/ktx/compiler
- Retrofit
- OkHttp + logging interceptor
- Kotlinx serialization json
- Converter de Kotlinx serialization para Retrofit
- Plugin KSP
- Plugin kotlin serialization

### 3.2 Editar `app/build.gradle.kts`

Plugins del modulo app:
- `com.android.application`
- `org.jetbrains.kotlin.plugin.compose`
- `org.jetbrains.kotlin.plugin.serialization`
- `com.google.devtools.ksp`

Dependencias clave:
- Compose BOM + ui/material3/activity-compose
- lifecycle-runtime-compose, lifecycle-viewmodel-compose
- coroutines-core/android
- room-runtime, room-ktx, ksp(room-compiler)
- retrofit + converter + okhttp + logging

### 3.3 Compatibilidad AGP/Kotlin

En algunos entornos nuevos, agrega en `gradle.properties`:
- `android.disallowKotlinSourceSets=false`

Y verificar wrapper Gradle:
- `gradle/wrapper/gradle-wrapper.properties` con `gradle-9.4.1-bin.zip` (o la version que pida tu AGP)

Checkpoint: Sync Gradle sin errores.

---

## 4) Estructura de carpetas (5 min)

Dentro de `app/src/main/java/bo/edu/umsa/curso/clase04/mvvm/` crear:

- `data/local`
- `data/remote`
- `data/repository`
- `domain`
- `ui`

Explicar: "no es obligatorio exacto, pero nos ayuda a separar responsabilidades".

---

## 5) Sesion 8 - MVVM y StateFlow (60 min)

## 5.1 Problema inicial (5 min)

Explicar rapidamente que pasa si todo esta en un composable:
- Logica mezclada con UI
- Difcil de testear
- Estado perdido al rotar/recrear

Plantear solucion: MVVM.

## 5.2 Crear estado de pantalla (10 min)

Archivo: `ui/NotesUiState.kt`

Crear `data class NotesUiState` con:
- `isLoading: Boolean`
- `query: String`
- `onlyPending: Boolean`
- `notes: List<NoteEntity>`
- `errorMessage: String?`

Mensaje clave: "la pantalla completa se representa con un objeto inmutable".

## 5.3 Crear ViewModel + encapsulamiento (20 min)

Archivo: `ui/NotesViewModel.kt`

Crear:
- `MutableStateFlow` privados (`_query`, `_onlyPending`, `_loading`, `_error`)
- `StateFlow<NotesUiState>` publico `uiState`
- `MutableSharedFlow<String>` para eventos de una sola vez (snackbar)

Explicar:
- `MutableStateFlow` se modifica dentro del ViewModel
- `StateFlow` expuesto a UI (solo lectura)
- `viewModelScope` vive con el ViewModel, se cancela automatico

Agregar funciones publicas (eventos desde UI):
- `onQueryChange(value)`
- `onOnlyPendingChange(value)`
- `addNote(...)`
- `toggleDone(...)`
- `syncRemote()`
- `clearError()`

## 5.4 Debounce y combine en Flow (10 min)

En el ViewModel:
- crear `debouncedQuery = _query.debounce(300)...`
- combinar flows para construir `uiState`

Idea didactica:
- input visual inmediato usa `_query`
- filtro de datos usa `debouncedQuery`

## 5.5 ViewModelFactory manual (5 min)

En el mismo archivo:
- crear `NotesViewModelFactory(repository)`

Explicar:
- sin Hilt para no saturar
- Factory manual enseña inyeccion de dependencias base

## 5.6 Compose observando estado (10 min)

Archivo: `ui/NotesScreen.kt`

En pantalla:
- `val state by viewModel.uiState.collectAsStateWithLifecycle()`
- `OutlinedTextField` (query)
- `Switch` (onlyPending)
- Botones `Agregar` y `Sync API`
- `LazyColumn` de notas

Mensaje clave:
- `collectAsStateWithLifecycle()` evita recolectar fuera de ciclo de vida activo.

Checkpoint sesion 8:
- Ya puedes escribir, agregar y cambiar estado local (aunque Room aun no este completo).

---

## 6) Sesion 9 - Room persistencia local (65 min)

## 6.1 Teoria corta (5 min)

Hablar de:
- SQLite nativo es verboso
- Room da verificacion en compilacion y menos boilerplate

## 6.2 Crear entidades (15 min)

Archivo: `data/local/Entities.kt`

Crear:
- `CategoryEntity` (`@Entity`, `@PrimaryKey`)
- `NoteEntity` (`@Entity`, `@ColumnInfo`, `@Ignore`)
- `NoteCategoryCrossRef` con `@ForeignKey`
- `NoteWithCategories` con `@Embedded`, `@Relation`, `@Junction`
- `NoteFts` con `@Fts4`

Puntos a explicar:
- `@Ignore` para campos no persistentes
- indices para consultas comunes
- relacion muchos-a-muchos

## 6.3 Crear DAO (15 min)

Archivo: `data/local/NoteDao.kt`

Agregar:
- `@Insert`, `@Update`, `@Delete` suspend
- `observeNotes(onlyPending): Flow<List<NoteEntity>>`
- query LIKE para busqueda
- query FTS para mostrar concepto de busqueda eficiente
- `@Transaction` para traer nota con categorias

Mensaje clave:
- `Flow<List<T>>` permite UI reactiva automatica.

## 6.4 Crear Database + migracion (10 min)

Archivo: `data/local/AppDatabase.kt`

Definir:
- `@Database(..., version = 2)`
- `abstract fun noteDao()`
- `MIGRATION_1_2` (ejemplo agregar columna `priority`)

Mensaje clave:
- nunca confiar en `fallbackToDestructiveMigration` para apps reales.

## 6.5 Conectar Room en MainActivity (10 min)

Archivo: `MainActivity.kt`

Crear instancia:
- `Room.databaseBuilder(...)`
- `.addMigrations(AppDatabase.MIGRATION_1_2)`

Pasar DAO al Repository.

## 6.6 Implementar Repository local (10 min)

Archivo: `data/repository/NotesRepository.kt`

Agregar funciones:
- `observeNotes(...)`
- `addNote(...)`
- `toggleDone(...)`
- `searchWithLike(...)` (si quieres mostrar opcion extra)

Enfatizar:
- ViewModel no habla con Room directamente.

Checkpoint sesion 9:
- cierro/reabro app y datos siguen ahi.

---

## 7) Sesion 10 - Coroutines, Flow y Retrofit (55 min)

## 7.1 Teoria corta de coroutines (8 min)

Explicar en pizarra:
- `launch` = fire-and-forget
- `async/await` = obtener resultado concurrente
- `Dispatchers.IO` para red/disco, `Main` para UI, `Default` para CPU
- `withContext` para cambiar dispatcher sin bloquear

## 7.2 Crear capa remota (15 min)

Archivo: `data/remote/RemoteApi.kt`

Crear DTO:
- `RemoteTodoDto` con `@Serializable` y `@SerialName`

Interfaz Retrofit:
- `@GET`, `@POST`, `@PUT`, `@DELETE`
- `@Query`, `@Path`, `@Header`, `@Body`

Factory Retrofit:
- `OkHttpClient` con timeouts
- `HttpLoggingInterceptor`
- converter Kotlinx Serialization
- baseUrl de JSONPlaceholder

## 7.3 Manejo de errores limpio (8 min)

Archivo: `domain/AppResult.kt`

Crear wrapper:
- `Success<T>`
- `Error(message)`

En Repository, al sync remoto:
- capturar `IOException`
- capturar `HttpException`
- capturar `Exception` generica

## 7.4 Integrar remoto en Repository y ViewModel (12 min)

En `NotesRepository`:
- `syncRemoteTodos()`:
  - llamar API
  - mapear a `NoteEntity`
  - insertar en Room
  - devolver `AppResult`

Mostrar `async/await` aqui para importar en paralelo (si aplica).

En `NotesViewModel`:
- `syncRemote()` actualiza loading/error y emite evento snackbar

## 7.5 UI de estado y eventos (12 min)

En `NotesScreen` + `MainActivity`:
- mostrar `CircularProgressIndicator` si `isLoading`
- mostrar `errorMessage` en rojo
- observar `events` con `LaunchedEffect` y mostrar `Snackbar`

Checkpoint final:
- Boton Sync trae items remotos
- lista se actualiza sola
- manejo de error visible

---

## 8) Orden exacto recomendado para escribir en vivo

1. Gradle/libs/plugins
2. Estructura de paquetes
3. Entidades Room
4. DAO
5. Database + migracion
6. Result wrapper
7. Retrofit API + factory
8. Repository
9. UiState
10. ViewModel + Factory
11. NotesScreen
12. MainActivity (wiring final)

Razón: siempre construyes de abajo hacia arriba y evitas errores de dependencias circulares al teclear.

---

## 9) Guion de narrativa para explicar mientras escribes

Frases cortas sugeridas:

- "Hoy vamos a separar UI de logica para que esto escale en proyectos reales."
- "Compose solo pinta estado, no decide reglas de negocio."
- "El ViewModel transforma eventos de UI en cambios de estado."
- "Repository es la unica puerta a datos."
- "Room nos da persistencia reactiva con Flow."
- "Retrofit trae remoto, Room consolida local, y la UI solo observa."

---

## 10) Errores comunes y como resolverlos rapido (seccion docente)

- Error de plugin Kotlin duplicado:
  - dejar solo los plugins necesarios y evitar combinaciones conflictivas segun AGP.

- `kotlin.sourceSets` con built-in Kotlin:
  - agregar `android.disallowKotlinSourceSets=false` en `gradle.properties` (si tu stack lo requiere).

- Wrapper Gradle incompatible:
  - actualizar `gradle-wrapper.properties` a version pedida por AGP.

- Converter de Kotlinx en Retrofit:
  - usar import correcto:
    - `com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory`

- TextField "no escribe":
  - separar estado visual inmediato de estado filtrado con debounce.

---

## 11) Actividades de cierre (10-15 min)

Reto 1:
- agregar boton "Limpiar busqueda"

Reto 2:
- mostrar contador de pendientes en UI

Reto 3:
- agregar prioridad editable (1-3) y ordenar por prioridad

Reto 4 (si hay tiempo):
- crear test simple de Repository con fake DAO

---

## 12) Material que puedes agregar luego (teoria extra)

- Diagrama MVVM (UI -> VM -> Repo -> Data)
- Diferencia StateFlow vs SharedFlow
- Por que LiveData se usa menos en proyectos nuevos
- Estrategias de cache offline-first
- Migrations reales y versionado de esquema

---

## 13) Checklist rapido antes de clase

- Proyecto compila en limpio
- Emulador abierto
- Internet funcional
- API JSONPlaceholder accesible
- Codigo final probado:
  - agregar nota
  - marcar completada
  - buscar
  - sync remoto
  - error de red visible

Si todo esto funciona, el demo esta listo para dictarse.
