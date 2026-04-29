# Módulo 4 Demo (MVVM + Room + Retrofit)

Demo intermedio pensado para escribirse en vivo y explicarse en **máximo 3 horas**.

## Objetivo pedagógico

Construir una app de notas simple que:
- usa **MVVM con `StateFlow`** para estado de pantalla
- persiste en local con **Room**
- sincroniza datos remotos con **Retrofit + Coroutines**

## Estructura de capas

- `ui`: Compose + `NotesViewModel` + `NotesUiState`
- `data/repository`: `NotesRepository` como punto único de acceso a datos
- `data/local`: Room (`@Entity`, `@Dao`, `@Database`, relaciones, FTS4, migración)
- `data/remote`: Retrofit + OkHttp + Kotlinx Serialization

## Plan sugerido de clase (180 min)

1. **Sesión 8 (60 min) — ViewModel y StateFlow**
   - Composable gordo vs arquitectura
   - `NotesUiState` como data class inmutable
   - `MutableStateFlow` interno + `StateFlow` expuesto
   - `viewModel()` + `NotesViewModelFactory`
   - `collectAsStateWithLifecycle()` en Compose
   - Eventos UI -> ViewModel (`addNote`, `toggleDone`, `syncRemote`)

2. **Sesión 9 (65 min) — Room**
   - `@Entity`, `@Dao`, `@Database`
   - `Flow<List<NoteEntity>>` desde DAO (reactivo)
   - Relaciones `@Relation`, `@Junction`, `@ForeignKey`
   - Búsqueda `LIKE`, filtro `WHERE`, orden `ORDER BY`
   - FTS4 (`notes_fts`) y migración `MIGRATION_1_2`

3. **Sesión 10 (55 min) — Coroutines, Flow y Retrofit**
   - `launch` vs `async/await` (en sync de API)
   - `withContext(Dispatchers.IO)` para disco/red
   - Retrofit (`@GET`, `@POST`, `@PUT`, `@DELETE`)
   - Parámetros `@Query`, `@Path`, `@Header`, `@Body`
   - Errores con `IOException`, `HttpException`, wrapper `AppResult`
   - Operadores `debounce`, `distinctUntilChanged`, `combine`, `zip`

## Mini reto final (10 min)

- Agregar prioridad editable en UI
- Mostrar contador de pendientes
- Añadir botón para limpiar error con `clearError()`
