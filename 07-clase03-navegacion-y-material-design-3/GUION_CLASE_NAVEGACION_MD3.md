# 🎓 Guión de Clase: Navegación y Material Design 3 en Compose
## Sesiones 6 & 7 — Total 2 Horas

---

## 📋 Índice
1. **Sesión 6: Navigation Compose** (60 minutos)
2. **Sesión 7: Material Design 3** (60 minutos)

---

## 🟣 Sesión 6: Navigation Compose (60 minutos)

### Concepto Inicial (5 min)
**¿Por qué necesitamos Navigation?**
- Una app real no tiene una sola pantalla
- Necesitamos gestionar el **back stack** (pila de navegación)
- Problemas sin Navigation Component: manejo manual de estados, deep links complicados, argumentos sin tipo

### Demo Setup (10 min)

#### Dependencia necesaria:
```kotlin
implementation("androidx.navigation:navigation-compose:2.8.5")
```

#### Componentes principales:
1. **NavController** - Gestiona la navegación (creado con `rememberNavController()`)
2. **NavHost** - Contenedor que define todas las pantallas
3. **composable()** - Define cada ruta/destino

### Rutas de Navegación (10 min)

**Archivo: `Screen.kt`**
```kotlin
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }
    data object Settings : Screen("settings")
}
```

**¿Por qué sealed class?**
- Type-safe: las rutas son objetos, no strings sueltos
- Proporciona autocomplete
- Imposible escribir mal una ruta

### NavHost y Rutas (15 min)

**Archivo: `NavGraph.kt`**
```kotlin
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Pantalla simple
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        
        // Pantalla con argumento tipado
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileScreen(userId = userId)
        }
    }
}
```

**Conceptos clave:**
- `startDestination`: Primera pantalla que se muestra
- `arguments`: Parámetros tipados y validados
- `backStackEntry`: Acceso a argumentos de la ruta

### Navegación Entre Pantallas (10 min)

**En las pantallas:**
```kotlin
// Navegar a ruta simple
navController.navigate(Screen.Search.route)

// Navegar con argumentos
navController.navigate(Screen.Profile.createRoute("juan_123"))

// Volver
navController.popBackStack()

// Volver a una pantalla específica
navController.navigateUp()
```

**Opciones avanzadas:**
```kotlin
navController.navigate(route) {
    // Limpia el back stack hasta la pantalla de inicio
    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    // Solo una instancia de esta ruta en el back stack
    launchSingleTop = true
    // Restaura el estado anterior
    restoreState = true
}
```

### BottomNavigation Conectada (10 min)

**Archivo: `MainActivity.kt`**
```kotlin
@Composable
fun MainApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        NavGraph(navController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, null) },
            label = { Text("Inicio") },
            // selected = true si estamos en Home
            selected = currentDestination?.hierarchy?.any {
                it.route == Screen.Home.route
            } ?: false,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(...) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
```

**`currentBackStackEntryAsState()`:**
- Observa cambios en la pila de navegación
- Permite resaltar el item activo en BottomNav
- Recomposición eficiente

---

## 🎨 Sesión 7: Material Design 3 en Compose (60 minutos)

### ¿Qué es Material Design 3? (5 min)

**Material Design 3 = Material You**
- Sistema de diseño oficial de Google (2022)
- Diferencias con MD2:
  - Colores más personalizables
  - Tipografía mejorada
  - Dynamic Color en Android 12+
  - Formas y elevación refinadas

**Los 3 pilares de MD3:**
1. **ColorScheme** - Colores primarios, secundarios, terciarios, etc.
2. **Typography** - Estilos de texto (display, headline, title, body, label)
3. **Shapes** - Esquinas redondeadas (extraSmall a extraLarge)

### ColorScheme (15 min)

**Archivo: `Color.kt`**
```kotlin
val LightPrimary = Color(0xFF6750a4)      // Púrpura
val LightOnPrimary = Color(0xFFffffff)    // Blanco (texto sobre púrpura)
val LightPrimaryContainer = Color(0xFFEADDFF)  // Púrpura claro
val LightOnPrimaryContainer = Color(0xFF21005d) // Púrpura oscuro
```

**Estructura de colores MD3:**
```
Primary (+ OnPrimary, PrimaryContainer, OnPrimaryContainer)
Secondary
Tertiary
Background, Surface, Error
...y más variantes
```

**Dark mode:**
```kotlin
val DarkPrimary = Color(0xFFd0bcff)  // Púrpura más claro
val DarkOnPrimary = Color(0xFF371e55) // Fondo púrpura oscuro
```

**Archivo: `Theme.kt`**
```kotlin
val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    // ... más colores
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    // ... más colores
)

@Composable
fun NavegacionMD3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
```

### Typography (10 min)

**Archivo: `Type.kt`**
```kotlin
val AppTypography = Typography(
    displayLarge = TextStyle(fontSize = 57.sp, fontWeight = FontWeight.Normal),
    headlineLarge = TextStyle(fontSize = 32.sp),
    titleLarge = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    bodyLarge = TextStyle(fontSize = 16.sp),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold),
    // ... más estilos
)
```

**Jerarquía tipográfica MD3:**
```
Display (Muy grande, para portadas)
  ↓
Headline (Encabezados principales)
  ↓
Title (Títulos de secciones)
  ↓
Body (Texto normal)
  ↓
Label (Etiquetas, botones)
```

**Uso en Compose:**
```kotlin
Text("Mi texto", style = MaterialTheme.typography.headlineSmall)
Text("Cuerpo", style = MaterialTheme.typography.bodyLarge)
```

### Shapes (5 min)

**Archivo: `Shape.kt`**
```kotlin
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
```

**Aplicación automática:**
- Botones usan `small`
- Cards usan `medium`
- Modales usan `large`

### Componentes MD3 Principales (20 min)

#### 1️⃣ TopAppBar
```kotlin
TopAppBar(
    title = { Text("Inicio") },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
)

// Variantes:
CenterAlignedTopAppBar(...)
LargeTopAppBar(...)
```

#### 2️⃣ NavigationBar & NavigationBarItem
```kotlin
NavigationBar {
    NavigationBarItem(
        icon = { Icon(Icons.Filled.Home, null) },
        label = { Text("Inicio") },
        selected = isSelected,
        onClick = { /* ... */ }
    )
}
```

#### 3️⃣ Cards
```kotlin
// Elevated Card (con sombra)
ElevatedCard {
    Text("Contenido")
}

// Outlined Card (con borde)
OutlinedCard {
    Text("Contenido")
}

// Regular Card
Card {
    Text("Contenido")
}
```

#### 4️⃣ FloatingActionButton
```kotlin
FloatingActionButton(
    onClick = { /* ... */ },
    containerColor = MaterialTheme.colorScheme.primary
) {
    Icon(Icons.Filled.Add, null)
}

// Extended FAB
ExtendedFloatingActionButton(
    onClick = { /* ... */ },
    icon = { Icon(Icons.Filled.Add, null) },
    text = { Text("Agregar") }
)
```

#### 5️⃣ Scaffold
```kotlin
Scaffold(
    topBar = { TopAppBar(...) },
    bottomBar = { NavigationBar(...) },
    floatingActionButton = { FloatingActionButton(...) },
    snackbarHost = { SnackbarHost(snackbarHostState) }
) { paddingValues ->
    // Contenido principal
    Column(modifier = Modifier.padding(paddingValues)) {
        // ...
    }
}
```

#### 6️⃣ Otros componentes
```kotlin
// TextField con outline
OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Etiqueta") }
)

// Switch
Switch(checked = enabled, onCheckedChange = { enabled = it })

// Chip
FilterChip(selected = selected, onClick = { /* ... */ }) {
    Text("Filtro")
}

// AlertDialog
AlertDialog(
    onDismissRequest = { },
    title = { Text("Título") },
    text = { Text("Mensaje") },
    confirmButton = { TextButton(onClick = { }) { Text("OK") } }
)
```

### Integración Completa (5 min)

**Flujo completo:**
```kotlin
// 1. En MainActivity
setContent {
    NavegacionMD3Theme {  // Aplica tema globalmente
        MainApp()
    }
}

// 2. MainApp usa Scaffold + Navigation
Scaffold(
    topBar = { /* TopAppBar MD3 */ },
    bottomBar = { /* NavigationBar conectada */ }
) {
    NavGraph(...)  // Usa rutas tipadas
}

// 3. Cada pantalla hereda los estilos MD3
Text("Automáticamente usa MaterialTheme.typography")
```

---

## 🚀 Ejercicios Prácticos (Sugerencias para ejercicios en clase)

### Ejercicio 1: Navegar entre pantallas (5 min)
- Hacer clic en botones para navegar
- Observar el back stack funcionando

### Ejercicio 2: Agregar una quinta pantalla (10 min)
- Crear `FavoritesScreen.kt`
- Agregar a `Screen.kt` y `NavGraph.kt`
- Conectar a BottomNav

### Ejercicio 3: Pasar argumentos (10 min)
- Modificar ProfileScreen para recibir más datos
- Ejemplo: `profile/{userId}/{name}`

### Ejercicio 4: Personalizar colores (10 min)
- Cambiar los colores primarios en `Color.kt`
- Observar cómo se actualiza toda la app

### Ejercicio 5: Agregar componentes MD3 (10 min)
- Agregar Chips en la pantalla de búsqueda
- Agregar BottomSheet en settings

---

## 📚 Referencias Rápidas

### Links Útiles
- [Material Design 3 Documentation](https://m3.material.io/)
- [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)
- [Compose Navigation Docs](https://developer.android.com/jetpack/compose/navigation)
- [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)

### Comandos útiles
```bash
# Compilar proyecto
./gradlew build

# Ejecutar en emulador
./gradlew installDebug

# Limpieza
./gradlew clean
```

---

## 💡 Tips para la Clase

1. **Demo en vivo:** Muestra cómo cambia el tema oscuro en Material Theme Builder
2. **Interactividad:** Permite que los estudiantes modifiquen colores en tiempo real
3. **Reutilización:** Explica cómo los componentes MD3 se automatizan con Scaffold
4. **Analogía:** Compara NavigationBar con pestañas en un navegador web
5. **Debugging:** Usa Logcat para mostrar qué ruta está activa

---

## 📝 Estructura del Proyecto

```
07-clase03-navegacion-y-material-design-3/
├── app/
│   ├── src/main/
│   │   ├── kotlin/bo/edu/umsa/curso/clase03/navegacion/
│   │   │   ├── MainActivity.kt              (Scaffold + BottomNav)
│   │   │   ├── navigation/
│   │   │   │   ├── Screen.kt               (Rutas tipadas)
│   │   │   │   └── NavGraph.kt             (Composables en rutas)
│   │   │   ├── screens/
│   │   │   │   ├── HomeScreen.kt           (Con FAB y Cards)
│   │   │   │   ├── SearchScreen.kt         (Con TextField)
│   │   │   │   ├── ProfileScreen.kt        (Recibe userId)
│   │   │   │   └── SettingsScreen.kt       (Con Switches)
│   │   │   └── ui/theme/
│   │   │       ├── Color.kt                (ColorScheme Light/Dark)
│   │   │       ├── Type.kt                 (Typography MD3)
│   │   │       ├── Shape.kt                (Shapes MD3)
│   │   │       └── Theme.kt                (MaterialTheme)
│   │   └── res/
│   │       ├── values/strings.xml
│   │       ├── values/colors.xml
│   │       ├── values/themes.xml
│   │       └── values-night/themes.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

---

**¡Listo para la clase!** 🎉

Duración: ~120 minutos
Dificultad: Intermedia (requiere conocimiento previo de Compose básico)
Herramientas: Android Studio Hedgehog o superior
