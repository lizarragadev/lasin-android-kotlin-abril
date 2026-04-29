# 📑 Índice del Proyecto — Navegación y Material Design 3

## 🎯 Inicio Rápido
1. Lee [README.md](./README.md) - Descripción general
2. Lee [GUION_CLASE_NAVEGACION_MD3.md](./GUION_CLASE_NAVEGACION_MD3.md) - Contenido de la clase
3. Lee [EJERCICIOS.md](./EJERCICIOS.md) - Ejercicios prácticos

---

## 📂 Estructura de Carpetas

```
07-clase03-navegacion-y-material-design-3/
├── README.md                          ← Lee primero
├── GUION_CLASE_NAVEGACION_MD3.md     ← Contenido de clase (120 min)
├── EJERCICIOS.md                      ← Ejercicios adicionales
├── INDEX.md                           ← Este archivo
├── settings.gradle.kts                ← Configuración de proyecto
├── build.gradle.kts                   ← Build root
├── gradle.properties                  ← Propiedades de gradle
├── local.properties                   ← SDK local (generado)
├── local.properties.example           ← Plantilla
├── .gitignore                         ← Archivos ignorados
├── gradle/                            ← Gradle wrapper
├── app/
│   ├── build.gradle.kts               ← Build app + dependencias
│   ├── proguard-rules.pro             ← Reglas ProGuard
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml    ← Manifest
│           ├── kotlin/
│           │   └── bo/edu/umsa/curso/clase03/navegacion/
│           │       ├── MainActivity.kt ✨ PUNTO DE ENTRADA
│           │       ├── navigation/
│           │       │   ├── Screen.kt       (Rutas tipadas)
│           │       │   └── NavGraph.kt     (NavHost + composable)
│           │       ├── screens/
│           │       │   ├── HomeScreen.kt
│           │       │   ├── SearchScreen.kt
│           │       │   ├── ProfileScreen.kt
│           │       │   └── SettingsScreen.kt
│           │       └── ui/theme/
│           │           ├── Color.kt       (Colores Light/Dark)
│           │           ├── Type.kt        (Tipografía MD3)
│           │           ├── Shape.kt       (Formas)
│           │           └── Theme.kt       (MaterialTheme)
│           └── res/
│               ├── values/
│               │   ├── strings.xml
│               │   ├── colors.xml
│               │   └── themes.xml
│               └── values-night/
│                   └── themes.xml
```

---

## 🎓 Contenido de la Clase

### Sesión 6: Navigation Compose (60 min)

**Conceptos:**
- NavController, NavHost, rutas tipadas
- Argumentos con tipo (navArgument)
- BottomNavigation reactiva
- Back stack management

**Archivos clave:**
- [Screen.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/navigation/Screen.kt)
- [NavGraph.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/navigation/NavGraph.kt)
- [MainActivity.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/MainActivity.kt)

**Demo en vivo:**
- Navegar entre 4 pantallas
- Ver BottomNav actualizado
- Pasar argumentos a ProfileScreen

---

### Sesión 7: Material Design 3 (60 min)

**Conceptos:**
- ColorScheme (Light/Dark)
- Typography (Display → Label)
- Shapes (ExtraSmall → ExtraLarge)
- Componentes MD3 nativos

**Archivos clave:**
- [Color.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/ui/theme/Color.kt)
- [Type.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/ui/theme/Type.kt)
- [Shape.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/ui/theme/Shape.kt)
- [Theme.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/ui/theme/Theme.kt)

**Componentes implementados:**
- [HomeScreen.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/screens/HomeScreen.kt) - TopAppBar, Cards, FAB
- [SearchScreen.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/screens/SearchScreen.kt) - TextField
- [ProfileScreen.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/screens/ProfileScreen.kt) - ElevatedCard, argumentos
- [SettingsScreen.kt](./app/src/main/kotlin/bo/edu/umsa/curso/clase03/navegacion/screens/SettingsScreen.kt) - Switch, Items

---

## 📊 Líneas de Código por Archivo

| Archivo | Líneas | Propósito |
|---------|--------|----------|
| MainActivity.kt | ~90 | Punto de entrada, Scaffold, BottomNav |
| Screen.kt | ~12 | Rutas tipadas |
| NavGraph.kt | ~45 | NavHost + rutas |
| HomeScreen.kt | ~65 | Cards, FAB, TopAppBar |
| SearchScreen.kt | ~35 | TextField |
| ProfileScreen.kt | ~55 | Argumentos, ElevatedCard |
| SettingsScreen.kt | ~85 | Switch, componentes complejos |
| Color.kt | ~50 | ColorScheme Light/Dark |
| Type.kt | ~90 | Tipografía completa |
| Shape.kt | ~12 | Formas |
| Theme.kt | ~60 | MaterialTheme |
| **Total** | **~600** | **Proyecto completo** |

---

## 🚀 Cómo Usar Este Proyecto

### Para estudiantes:
1. [ ] Clone/descargue el proyecto
2. [ ] Abra en Android Studio
3. [ ] Ejecute en emulador
4. [ ] Siga los pasos del [GUION_CLASE_NAVEGACION_MD3.md](./GUION_CLASE_NAVEGACION_MD3.md)
5. [ ] Realice los [EJERCICIOS.md](./EJERCICIOS.md)

### Para instructores:
1. [ ] Revise la estructura en Android Studio
2. [ ] Use el guión para la presentación (tiene tiempos)
3. [ ] Proyecte el código en clase
4. [ ] Modifique valores en vivo (colores, tipografía)
5. [ ] Asigne ejercicios a los estudiantes

---

## 🎯 Mapa Conceptual de Navegación

```
NavController
    ↓
rememberNavController()
    ↓
NavHost
    ├─ composable(Screen.Home.route) → HomeScreen()
    ├─ composable(Screen.Search.route) → SearchScreen()
    ├─ composable(Screen.Profile.route, args) → ProfileScreen()
    └─ composable(Screen.Settings.route) → SettingsScreen()
    
currentBackStackEntryAsState()
    ↓
BottomNavigationBar
    ├─ NavigationBarItem(selected = true)
    ├─ NavigationBarItem(selected = false)
    └─ ...
```

---

## 🎨 Mapa Conceptual de Material Design 3

```
MaterialTheme
    ├─ ColorScheme
    │   ├─ Primary + OnPrimary + PrimaryContainer
    │   ├─ Secondary + ...
    │   ├─ Tertiary + ...
    │   ├─ Background, Surface, Error
    │   └─ (Light & Dark variants)
    │
    ├─ Typography
    │   ├─ Display (3 tamaños)
    │   ├─ Headline (3 tamaños)
    │   ├─ Title (3 tamaños)
    │   ├─ Body (3 tamaños)
    │   └─ Label (3 tamaños)
    │
    └─ Shapes
        ├─ ExtraSmall (4 dp)
        ├─ Small (8 dp)
        ├─ Medium (12 dp)
        ├─ Large (16 dp)
        └─ ExtraLarge (28 dp)
```

---

## 💡 Tips para Enseñanza

### En vivo (Live Coding):
1. Modifica `Color.kt` → Aplica → Observa cambios en tiempo real
2. Abre Material Theme Builder en navegador (junto a Android Studio)
3. Muestra debug panel de Navigation
4. Alterna entre Light/Dark en el emulador

### Interactividad:
1. Pide a estudiantes que sugieran colores
2. Permite que cambien tipografía
3. Muestra errores de compilación deliberados
4. Explica cómo los arregla

### Analogías útiles:
- NavController = Telecomandó de TV
- NavHost = Todas las canales disponibles
- BottomNav = Controles de volumen/canales
- MaterialTheme = Tema del sistema operativo

---

## 📖 Glosario de Términos

| Término | Definición |
|---------|-----------|
| **NavController** | Objeto que maneja la navegación entre pantallas |
| **NavHost** | Composable que contiene todas las rutas |
| **Route** | String que identifica una pantalla (ej: "home") |
| **composable()** | Función que define una pantalla en NavHost |
| **Screen** | Sealed class que agrupa todas las rutas |
| **currentBackStackEntryAsState()** | Observa la pantalla activa |
| **navArgument()** | Define argumentos tipados para rutas |
| **ColorScheme** | Define la paleta de colores de MD3 |
| **Typography** | Define los estilos de texto de MD3 |
| **Shapes** | Define las formas redondeadas de MD3 |
| **MaterialTheme** | Composable que aplica tema global |
| **Scaffold** | Estructura base de pantalla (app bar, nav, FAB) |

---

## ✅ Checklist de Completitud

### Archivos de Configuración:
- [ ] build.gradle.kts (root)
- [ ] settings.gradle.kts
- [ ] gradle.properties
- [ ] local.properties
- [ ] app/build.gradle.kts

### Código Kotlin:
- [ ] MainActivity.kt
- [ ] Screen.kt
- [ ] NavGraph.kt
- [ ] HomeScreen.kt, SearchScreen.kt, ProfileScreen.kt, SettingsScreen.kt
- [ ] Color.kt, Type.kt, Shape.kt, Theme.kt

### Recursos (res/):
- [ ] AndroidManifest.xml
- [ ] strings.xml, colors.xml, themes.xml
- [ ] values-night/themes.xml

### Documentación:
- [ ] README.md
- [ ] GUION_CLASE_NAVEGACION_MD3.md
- [ ] EJERCICIOS.md
- [ ] INDEX.md (este archivo)

### Tests (Opcional):
- [ ] Unit tests (src/test/)
- [ ] Instrumented tests (src/androidTest/)

---

## 🔗 Enlaces Rápidos

**Documentación Oficial:**
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Material Design 3](https://m3.material.io/)
- [Compose API Reference](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary)

**Herramientas:**
- [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)
- [Android Studio](https://developer.android.com/studio)
- [Kotlin Playground](https://kotlinlang.org/docs/tutorials/koans.html)

**Comunidad:**
- [Android Developers](https://developer.android.com/)
- [Stack Overflow (android-compose)](https://stackoverflow.com/questions/tagged/android-compose)
- [Reddit r/androiddev](https://reddit.com/r/androiddev)

---

## 📞 Soporte y Preguntas

### P&R Frecuentes:
Ver [EJERCICIOS.md](./EJERCICIOS.md#-respuesta-a-preguntas-frecuentes)

### Errores Comunes:
1. "Cannot find symbol NavHost" → Sincronizar Gradle
2. "App en blanco" → Verificar Theme en MainActivity
3. "BottomNav no responde" → Verificar navController en Scaffold

### Contacto:
- Para dudas de clase: Consulta durante la sesión
- Para dudas de código: Revisa los comentarios en los archivos
- Para mejoras: Abre un issue o pull request

---

## 📈 Progresión de Complejidad

```
Básico ────────────────────────── Avanzado

Screen.kt           ← Empezar aquí (rutas simples)
     ↓
NavGraph.kt         ← Agregar composables
     ↓
HomeScreen.kt       ← Primer componente
     ↓
MainActivity.kt     ← Conectar todo
     ↓
Color.kt + Type.kt  ← Material Design 3
     ↓
Theme.kt            ← Tema global
     ↓
EJERCICIOS.md       ← Aplicar conocimiento
```

---

## 🎓 Resultados de Aprendizaje

Al completar este módulo, los estudiantes podrán:

✅ Implementar navegación tipada con Navigation Compose  
✅ Conectar UI reactiva a cambios de ruta  
✅ Diseñar apps con Material Design 3  
✅ Pasar argumentos tipados entre pantallas  
✅ Gestionar el back stack efectivamente  
✅ Personalizar temas (colores, tipografía, formas)  
✅ Usar componentes MD3 nativos correctamente  
✅ Crear interfaces consistentes y accesibles  

---

## 📝 Notas de Versión

| Versión | Fecha | Cambios |
|---------|-------|---------|
| 1.0 | Abril 2026 | Versión inicial |
| | | - 4 pantallas de demo |
| | | - Navigation Compose 2.8.5 |
| | | - Material Design 3 |
| | | - Guión de 2 horas |
| | | - 7 ejercicios prácticos |

---

## 📄 Licencia

Proyecto educativo. Libre para uso en clase, modificación y distribución.

---

**Última actualización:** Abril 2026  
**Versión:** 1.0  
**Estado:** Listo para clase ✅

