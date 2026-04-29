# Navegación y Material Design 3 en Compose

![Clase 03](https://img.shields.io/badge/Clase-03-blue)
![Sessions](https://img.shields.io/badge/Sesiones-2-green)
![Duration](https://img.shields.io/badge/Duración-2%20horas-orange)
![Android](https://img.shields.io/badge/Android-14%2B-brightgreen)

## 📌 Descripción

Proyecto educativo que implementa **Navigation Compose** y **Material Design 3** de forma completa y práctica. Incluye:

- ✅ Estructura de navegación con NavHost, NavController y rutas tipadas
- ✅ BottomNavigationBar integrada con currentBackStackEntryAsState()
- ✅ Paso de argumentos tipados entre pantallas
- ✅ Material Design 3 completo (ColorScheme, Typography, Shapes)
- ✅ Dark mode automático
- ✅ Componentes MD3 nativos (Cards, FAB, TopAppBar, etc.)
- ✅ Guión de clase detallado (120 minutos)

## 🚀 Inicio Rápido

### Requisitos
- Android Studio Hedgehog o superior
- JDK 17+
- SDK Android 35

### Pasos para ejecutar
```bash
# 1. Clonar/descargar el proyecto
cd 07-clase03-navegacion-y-material-design-3

# 2. Abrir en Android Studio (File > Open)

# 3. Sincronizar Gradle
# En Android Studio: Tools > Sync Now

# 4. Ejecutar en emulador
# Run > Run 'app'
```

## 📚 Contenido del Módulo

### Sesión 6: Navigation Compose (60 min)
| Tema | Archivo Clave | Líneas |
|------|---------------|--------|
| Rutas tipadas | `Screen.kt` | 10 |
| NavGraph | `NavGraph.kt` | 45 |
| BottomNavigation | `MainActivity.kt` | 80 |
| Argumentos | `ProfileScreen.kt` | 15 |

**Conceptos cubiertos:**
- NavController y rememberNavController()
- NavHost y composable()
- Sealed class para rutas
- navArgument() con tipos
- currentBackStackEntryAsState() para UI reactiva

### Sesión 7: Material Design 3 (60 min)
| Componente | Archivo | Ejemplo |
|-----------|---------|---------|
| ColorScheme | `Color.kt`, `Theme.kt` | Light/Dark |
| Typography | `Type.kt` | Display a Label |
| Shapes | `Shape.kt` | ExtraSmall a ExtraLarge |
| TopAppBar | `HomeScreen.kt` | Con icono |
| Cards | `HomeScreen.kt` | Elevated + Outlined |
| FAB | `HomeScreen.kt` | FloatingActionButton |
| BottomNav | `MainActivity.kt` | Integrado |

**Componentes implementados:**
```kotlin
✓ TopAppBar
✓ NavigationBar / NavigationBarItem
✓ Card / ElevatedCard / OutlinedCard
✓ FloatingActionButton
✓ Scaffold (estructura base)
✓ OutlinedTextField
✓ Switch
✓ Icon
```

## 📂 Estructura del Proyecto

```
app/src/main/
├── kotlin/bo/edu/umsa/curso/clase03/navegacion/
│   ├── MainActivity.kt
│   ├── navigation/
│   │   ├── Screen.kt
│   │   └── NavGraph.kt
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── SearchScreen.kt
│   │   ├── ProfileScreen.kt
│   │   └── SettingsScreen.kt
│   └── ui/theme/
│       ├── Color.kt          (101 líneas, colores Light/Dark)
│       ├── Type.kt           (88 líneas, tipografía MD3)
│       ├── Shape.kt          (10 líneas, formas)
│       └── Theme.kt          (56 líneas, MaterialTheme)
└── res/
    ├── values/
    │   ├── strings.xml
    │   ├── colors.xml       (Colores XML para XML resources)
    │   └── themes.xml       (Tema XML)
    └── values-night/
        └── themes.xml       (Tema oscuro XML)
```

## 🎯 Pantallas de la App

### 1. Home Screen 🏠
- TopAppBar personalizado
- Card elevado y Card outlined
- FloatingActionButton
- Botón para navegar a Profile

### 2. Search Screen 🔍
- OutlinedTextField
- TextField con icono

### 3. Profile Screen 👤
- Recibe argumentos (userId)
- ElevatedCard con información
- Avatar (Icon como círculo)

### 4. Settings Screen ⚙️
- Switch personalizado
- Componente reutilizable SettingItem
- Iconos de settings

## 🎨 Tema Material Design 3

### Paleta de Colores
```kotlin
// Modo Light
Primary: #6750a4 (Púrpura)
Secondary: #625b71 (Gris púrpura)
Tertiary: #7d5260 (Marrón)

// Modo Dark
Primary: #d0bcff (Púrpura claro)
Secondary: #ccc7db (Gris claro)
Tertiary: #f5b7ce (Rosa)
```

### Tipografía
```
DisplayLarge   → 57 sp
HeadlineLarge  → 32 sp
TitleLarge     → 22 sp (Bold)
BodyLarge      → 16 sp
LabelSmall     → 11 sp (Bold)
```

### Formas
```
ExtraSmall   → 4 dp
Small        → 8 dp
Medium       → 12 dp
Large        → 16 dp
ExtraLarge   → 28 dp
```

## 💻 Dependencias Clave

```gradle
androidx.navigation:navigation-compose:2.8.5
androidx.compose.material3:material3
androidx.compose.material:material-icons-extended
```

## 🔍 Puntos de Interés para Clase

### 1. Rutas Tipadas (Screen.kt)
```kotlin
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }
}
```
**¿Por qué?** Type-safe, proporciona autocomplete, evita errores de tipeo

### 2. BottomNavigation Reactiva (MainActivity.kt)
```kotlin
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentDestination = navBackStackEntry?.destination

NavigationBarItem(
    selected = currentDestination?.hierarchy?.any { 
        it.route == Screen.Home.route 
    } ?: false,
    // ...
)
```
**¿Por qué?** La UI se actualiza automáticamente según la ruta activa

### 3. Argumentos Tipados (NavGraph.kt)
```kotlin
arguments = listOf(
    navArgument("userId") {
        type = NavType.StringType
        nullable = false
    }
)
```
**¿Por qué?** Validación de tipos en tiempo de compilación

### 4. Tema Unificado (Theme.kt)
```kotlin
MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    shapes = AppShapes,
    content = content
)
```
**¿Por qué?** Consistencia visual en toda la app con Material Design 3

## 📋 Ejercicios Sugeridos

1. **Agregar nueva pantalla** (10 min)
   - Crear `FavoritesScreen.kt`
   - Agregarlo a Screen.kt y NavGraph.kt
   - Conectar a BottomNav

2. **Modificar colores** (5 min)
   - Cambiar `LightPrimary` en Color.kt
   - Observar cómo se propaga por toda la app

3. **Pasar más argumentos** (10 min)
   - Modificar ProfileScreen para recibir `profile/{userId}/{name}`
   - Actualizar NavGraph y HomeScreen

4. **Agregar componentes** (15 min)
   - Agregar Chip en SearchScreen
   - Agregar BottomSheet en HomeScreen

## 🧪 Testing

El proyecto es una demo educativa. Para testing en producción:
- Unit tests: `src/test/`
- Instrumented tests: `src/androidTest/`

## 📖 Guía de Clase

Ver archivo: `GUION_CLASE_NAVEGACION_MD3.md`

Incluye:
- ✅ Explicación de cada concepto
- ✅ Código de ejemplo
- ✅ Ejercicios prácticos
- ✅ Tips para la enseñanza
- ✅ Referencias

## 🎓 Nivel Educativo

**Prerrequisitos:**
- Kotlin básico (variables, funciones, clases)
- Jetpack Compose básico (Composables, state, Modifier)
- Android Studio familiarizado

**Resultados de aprendizaje:**
- ✅ Implementar navegación tipada
- ✅ Conectar UI reactiva a la navegación
- ✅ Diseñar apps con Material Design 3
- ✅ Pasar argumentos entre pantallas
- ✅ Gestionar back stack efectivamente

## 🚨 Resolución de Problemas

### Error: "Cannot find symbol NavHost"
```
→ Verificar que navigation-compose está en build.gradle.kts
→ Hacer Sync > Clean Build
```

### Error: "Activity not found"
```
→ Verificar namespace en AndroidManifest.xml
→ Verificar que MainActivity está registrada en manifest
```

### App muestra pantalla en blanco
```
→ Verificar que Material3Theme envuelve content
→ Verificar que NavGraph recibe navController válido
```

## 📞 Soporte

Para dudas sobre el contenido, ver:
- `GUION_CLASE_NAVEGACION_MD3.md` - Explicaciones detalladas
- Código comentado en cada archivo

## 📄 Licencia

Proyecto educativo. Libre para uso en clase y modificación.

---

**Creado:** Abril 2026  
**Duración recomendada:** 120 minutos  
**Nivel:** Intermedio  
**IDE:** Android Studio Hedgehog+

