# 🏋️ Ejercicios Prácticos — Navegación y Material Design 3

## Nivel: Intermedio
## Duración: 45-60 minutos (opcionales después de la clase)

---

## ✅ Ejercicio 1: Exploración Básica (10 min)

**Objetivo:** Familiarizarse con la estructura del proyecto

### Tareas:
1. [ ] Ejecutar la app en el emulador
2. [ ] Hacer clic en cada elemento de BottomNav
3. [ ] Observar cómo cambian las pantallas
4. [ ] Hacer clic en "Ver Perfil de Usuario" en HomeScreen
5. [ ] Observar que el BottomNav se actualiza cuando navegas

**Preguntas:**
- ¿Qué pasa cuando haces clic en el botón de atrás?
- ¿El ID del usuario cambió en la pantalla de perfil?

---

## ✅ Ejercicio 2: Agregar una Nueva Pantalla (20 min)

**Objetivo:** Entender el flujo completo de navegación

### Tareas:

#### Paso 1: Crear el archivo de pantalla
Crea `app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/screens/FavoritesScreen.kt`:

```kotlin
package bo.edu.umsa.curso.clase03.navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Pantalla de Favoritos",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
```

#### Paso 2: Agregar ruta en Screen.kt
```kotlin
data object Favorites : Screen("favorites")
```

#### Paso 3: Agregar en NavGraph.kt
```kotlin
composable(route = Screen.Favorites.route) {
    FavoritesScreen()
}
```

#### Paso 4: Agregar a BottomNav en MainActivity.kt
Agrega esta línea en el constructor de `items`:
```kotlin
Screen.Favorites,
```

Y agrega este caso en el when del onClick:
```kotlin
Screen.Favorites -> "favorites"
```

También en el Icon/Label del NavigationBarItem:
```kotlin
is Screen.Favorites -> Icon(Icons.Filled.Favorite, null)
is Screen.Favorites -> Text("Favoritos")
```

**Verificación:**
- [ ] El nuevo item aparece en BottomNav
- [ ] Puedes navegar a la pantalla
- [ ] El item se resalta cuando estás en esa pantalla

---

## ✅ Ejercicio 3: Personalizar Colores (15 min)

**Objetivo:** Entender cómo funciona el sistema de colores MD3

### Tareas:

1. Abre `Color.kt`
2. Cambia `LightPrimary` a un color diferente:
   ```kotlin
   // Antes:
   val LightPrimary = Color(0xFF6750a4)
   
   // Después (ej: naranja):
   val LightPrimary = Color(0xFFFF6B35)
   ```

3. Reconstruye la app (`Build > Rebuild Project`)
4. Ejecuta la app

**Observaciones:**
- Los TopAppBars que usan `primaryContainer` cambiaron
- Los Buttons cambiaron
- Las Cards pueden haberse actualizado

5. Cambia también `LightOnPrimary` para asegurar buen contraste

**Colores sugeridos para experimentar:**
```kotlin
// Verde esmeralda
val LightPrimary = Color(0xFF00897B)

// Azul océano
val LightPrimary = Color(0xFF0288D1)

// Rojo coral
val LightPrimary = Color(0xFFD32F2F)

// Púrpura profundo
val LightPrimary = Color(0xFF7B1FA2)
```

**Desafío:** Cambia también `LightSecondary` en la pantalla de búsqueda

---

## ✅ Ejercicio 4: Pasar Más Argumentos (15 min)

**Objetivo:** Entender argumentos tipados complejos

### Tareas:

#### Paso 1: Modificar Screen.kt
```kotlin
data object Profile : Screen("profile/{userId}/{userName}") {
    fun createRoute(userId: String, userName: String) = "profile/$userId/$userName"
}
```

#### Paso 2: Actualizar NavGraph.kt
```kotlin
composable(
    route = Screen.Profile.route,
    arguments = listOf(
        navArgument("userId") {
            type = NavType.StringType
            nullable = false
        },
        navArgument("userName") {
            type = NavType.StringType
            nullable = false
        }
    )
) { backStackEntry ->
    val userId = backStackEntry.arguments?.getString("userId") ?: ""
    val userName = backStackEntry.arguments?.getString("userName") ?: ""
    ProfileScreen(userId = userId, userName = userName)
}
```

#### Paso 3: Modificar ProfileScreen.kt
```kotlin
@Composable
fun ProfileScreen(userId: String, userName: String = "") {
    // ...
    Text(
        text = userName.ifEmpty { "Usuario ID: $userId" },
        style = MaterialTheme.typography.headlineSmall
    )
    // ...
}
```

#### Paso 4: Actualizar llamada en HomeScreen.kt
```kotlin
Button(onClick = {
    onNavigateToProfile("juan_perez_123", "Juan Pérez")
}) {
    Text("Ver Perfil de Usuario")
}
```

**Verificación:**
- [ ] El nombre aparece en ProfileScreen
- [ ] La navegación funciona

---

## ✅ Ejercicio 5: Agregar Componentes MD3 (20 min)

**Objetivo:** Practicar con componentes Material Design 3

### Opción A: Agregar Chips en SearchScreen
En `SearchScreen.kt`, agrega después del TextField:

```kotlin
Spacer(modifier = Modifier.height(16.dp))
Text("Filtros", style = MaterialTheme.typography.labelLarge)
Spacer(modifier = Modifier.height(8.dp))

Row(
    modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    FilterChip(
        selected = false,
        onClick = { },
        label = { Text("Android") }
    )
    FilterChip(
        selected = true,
        onClick = { },
        label = { Text("Compose") }
    )
    FilterChip(
        selected = false,
        onClick = { },
        label = { Text("Material") }
    )
}
```

**No olvides agregar imports:**
```kotlin
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Row
```

### Opción B: Agregar Dialog en HomeScreen
En `HomeScreen.kt`, agrega state y button:

```kotlin
val showDialog = remember { mutableStateOf(false) }

// En el Column, agrega:
Button(onClick = { showDialog.value = true }) {
    Text("Mostrar Diálogo")
}

// Fuera del Column, agrega:
if (showDialog.value) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text("Material Design 3") },
        text = { Text("Este es un AlertDialog en MD3") },
        confirmButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text("OK")
            }
        }
    )
}
```

---

## ✅ Ejercicio 6: Cambiar Tipografía (10 min)

**Objetivo:** Personalizar estilos de texto

### Tareas:

1. Abre `Type.kt`
2. Cambia `headlineSmall` a un tamaño más grande:
   ```kotlin
   headlineSmall = TextStyle(
       fontFamily = FontFamily.Default,
       fontWeight = FontWeight.Normal,
       fontSize = 30.sp,  // Era 24.sp
       lineHeight = 36.sp // Era 32.sp
   )
   ```

3. Ejecuta la app
4. Observa que todos los `headlineSmall` en la app son más grandes

**Desafío:** Cambia la tipografía de `titleMedium` en todas las pantallas

---

## ✅ Ejercicio 7: Modo Oscuro (10 min)

**Objetivo:** Testear el tema oscuro

### Tareas:

1. En el emulador, activa el modo oscuro:
   - Settings → Display → Dark theme (ON)
   
2. O en Android Studio:
   - Run → Toggle system appearance

3. Observa:
   - [ ] Los colores cambian automáticamente
   - [ ] El texto es visible en ambos modos
   - [ ] Las Cards mantienen contraste

4. Modifica `Theme.kt` para cambiar el comportamiento:
   ```kotlin
   @Composable
   fun NavegacionMD3Theme(
       darkTheme: Boolean = isSystemInDarkTheme(),  // O fuerza: true/false
       content: @Composable () -> Unit
   )
   ```

---

## 🚀 Ejercicio Integrador: Crear una App Mini

**Duración:** 30 minutos  
**Objetivo:** Usar todo lo aprendido

### Requisitos:
1. Crear una pantalla "Tareas" con lista de tareas
2. Poder marcar tareas como completas (Switch)
3. Agregar nuevas tareas (FAB)
4. Mantener tema MD3 consistente
5. Pasar datos mediante argumentos

**Estructura sugerida:**
```kotlin
// Screen.kt - Agregar
data object Tasks : Screen("tasks/{categoryId}") {
    fun createRoute(categoryId: String) = "tasks/$categoryId"
}

// TasksScreen.kt - Nueva pantalla
@Composable
fun TasksScreen(categoryId: String) {
    // Tu código aquí
}

// En NavGraph - Agregar
composable(
    route = Screen.Tasks.route,
    arguments = listOf(
        navArgument("categoryId") { type = NavType.StringType }
    )
) { backStackEntry ->
    val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
    TasksScreen(categoryId = categoryId)
}
```

---

## 📚 Respuesta a Preguntas Frecuentes

### P: ¿Por qué necesito `rememberNavController()`?
R: Para que el NavController persista a través de recomposiciones. Sin `remember`, se crearía uno nuevo cada vez.

### P: ¿Puedo pasar objetos complejos entre pantallas?
R: Con Navigation Compose 2.8+, puedes usar Kotlinx Serialization:
```gradle
implementation("androidx.navigation:navigation-compose:2.8.5")
```

### P: ¿Cómo hago que una pantalla no se vuelva a compicar al navegación?
R: Usa `launchSingleTop = true` en `navigate()`

### P: ¿Por qué desaparece mi estado cuando cambio de pantalla?
R: Los Composables se descomponen. Usa `ViewModel` o `SavedStateHandle` para mantener estado.

---

## 🎯 Checklist de Comprensión

Antes de considerar completado este tema, asegúrate de entender:

- [ ] Diferencia entre `Screen.route` y `Screen.createRoute()`
- [ ] Por qué usamos `sealed class` para rutas
- [ ] Cómo `currentBackStackEntryAsState()` actualiza el BottomNav
- [ ] Qué es `navArgument()` y por qué es tipado
- [ ] Los 3 pilares de Material Design 3 (Color, Type, Shape)
- [ ] Cómo `lightColorScheme()` y `darkColorScheme()` funcionan
- [ ] Diferencia entre `Card`, `ElevatedCard`, `OutlinedCard`
- [ ] Qué es `Scaffold` y por qué usarlo

---

## 📖 Referencias para más información

- [Navigation Compose Docs](https://developer.android.com/jetpack/compose/navigation)
- [Material Design 3 Guidelines](https://m3.material.io/)
- [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)
- [Compose API Reference](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary)

---

**¡Buen trabajo!** 🎉

Estos ejercicios están diseñados para reforzar los conceptos de la clase.  
Considera hacerlos después de cada sesión para máximo aprendizaje.
