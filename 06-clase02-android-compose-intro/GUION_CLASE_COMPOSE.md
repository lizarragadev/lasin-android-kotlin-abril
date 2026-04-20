# Guion de Clase: Jetpack Compose (4 Horas)

Este documento es la guía oficial para las Sesiones 4 y 5. Sigue el orden de los archivos del proyecto para construir la interfaz y explicar los conceptos fundamentales.

---

## SESIÓN 4: Anatomía y Primer Contacto

### 1. El punto de entrada (`MainActivity.kt`)
**Qué y Por qué:** Antes de ver la UI, debemos entender cómo arranca la app. Sustituimos el tradicional `setContentView(R.layout...)` por `setContent { ... }`, que es el puente entre la Activity clásica y el mundo Compose.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita que la UI se dibuje detrás de las barras del sistema
        setContent { // Define la raíz de nuestra interfaz con Compose
            Clase02ComposeIntroTheme { // Aplica el tema de diseño (colores, tipos) definido en el proyecto
                DemoComposeIntroScreen() // Llamada a nuestra pantalla principal
            }
        }
    }
}
```
**Conclusión:** La Activity ahora es solo un contenedor vacío. Todo el control de la UI pasa a las funciones Composable.

---

### 2. Estructura de Pantalla (`DemoComposeIntroScreen`)
**Qué y Por qué:** Usamos `Scaffold` para implementar la estructura visual estándar de Material Design (barra superior, contenido, etc.) y `Column` con scroll para organizar los elementos.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoComposeIntroScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Ocupa todo el espacio disponible
        topBar = {
            TopAppBar(title = { Text("Compose — Clase 2") }) // Barra de título superior
        },
    ) { innerPadding -> // Scaffold nos da este padding para evitar que el contenido tape la TopBar
        Column(
            modifier = Modifier
                .padding(innerPadding) // Aplica el padding del Scaffold
                .padding(horizontal = 20.dp) // Añade margen lateral de 20dp
                .fillMaxSize() // Se expande en toda el área restante
                .verticalScroll(rememberScrollState()), // Permite hacer scroll si el contenido supera la pantalla
            verticalArrangement = Arrangement.spacedBy(16.dp), // Espacio automático entre hijos
        ) {
            // Secciones de la pantalla
            Text("Bienvenido a Jetpack Compose", style = MaterialTheme.typography.headlineSmall)
            
            // Botón simple con Row e Icono para demostrar composición básica
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.School, contentDescription = null)
                    Text("Botón con ícono", modifier = Modifier.padding(start = 8.dp))
                }
            }
            
            HorizontalDivider() // Línea divisoria visual
            ContadorYNombre() // Segunda sección: Lógica de Estado
            HorizontalDivider()
            SeccionEjemplosMaterial3() // Tercera sección: Componentes extras
        }
    }
}
```
**Conclusión:** El layout se construye mediante la anidación de funciones. `Scaffold` y `Column` son los pilares de la organización.

---

### 3. Estado y Recomposición (`ContadorYNombre`)
**Qué y Por qué:** Aquí explicamos cómo Compose reacciona a los datos. Si una variable cambia, Compose vuelve a dibujar solo la parte afectada. `rememberSaveable` es clave para no perder datos al rotar el móvil.

```kotlin
@Composable
private fun ContadorYNombre() {
    // Definimos estados mutables que Compose "observará"
    var nombre by rememberSaveable { mutableStateOf("") }
    var contador by rememberSaveable { mutableIntStateOf(0) }

    OutlinedTextField(
        value = nombre, // El campo siempre muestra el valor actual del estado
        onValueChange = { nombre = it }, // Actualiza el estado con cada tecla pulsada
        label = { Text("Tu nombre") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Text(
        text = if (nombre.isBlank()) "Hola — pulsa para sumar" else "Hola $nombre, contador = $contador",
        style = MaterialTheme.typography.bodyLarge
    )

    Button(onClick = { contador++ }) { // Al cambiar 'contador', el Text de arriba se redibuja solo
        Icon(Icons.Default.Add, contentDescription = null)
        Text("Incrementar", modifier = Modifier.padding(start = 8.dp))
    }
}
```
**Conclusión:** El flujo es unidireccional: El usuario interactúa -> El estado cambia -> La UI se recompone.

---

## SESIÓN 5: Modificadores, Listas y Animaciones (`ComposeClase2Extras.kt`)

### 4. Modificadores y Box (`EjemploBoxEImagen`)
**Qué y Por qué:** Demostramos el uso de `Box` para superponer elementos y cómo el orden de los modificadores altera el resultado visual (padding interno vs externo).

```kotlin
@Composable
fun EjemploBoxEImagen() {
    Box(
        modifier = Modifier
            .size(100.dp) // Tamaño fijo
            .clip(RoundedCornerShape(16.dp)) // Recorta las esquinas
            .background(MaterialTheme.colorScheme.primaryContainer) // Color de fondo
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)) // Borde
            .clickable { }, // Añade interacción táctil
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Logo")
        Text("M3", modifier = Modifier.align(Alignment.BottomEnd)) // Alineación específica en el eje Z
    }
}
```

---

### 5. State Hoisting y Animación (`EjemploHoistingYAnimacion`)
**Qué y Por qué:** Explicamos cómo elevar el estado para que un componente sea reutilizable y cómo usar `AnimatedVisibility` para efectos visuales sencillos.

```kotlin
@Composable
fun EjemploHoistingYAnimacion(estaVisible: Boolean, onToggle: () -> Unit) {
    // animateColorAsState: Crea una transición suave de color basada en un estado
    val colorAnimado by animateColorAsState(
        targetValue = if (estaVisible) MaterialTheme.colorScheme.primary else Color.Gray,
        label = "color"
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mostrar contenido:", color = colorAnimado)
            IconButton(onClick = onToggle) { // El evento sube al padre (Hoisting)
                Icon(if (estaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = null)
            }
        }
        
        AnimatedVisibility(visible = estaVisible) { // Gestiona automáticamente la animación de entrada/salida
            ElevatedCard {
                Text("¡Contenido animado!", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
```

---

### 6. Componentes M3 y Listas (`EjemploSeleccionYWeight` y `EjemploLazyList`)
**Qué y Por qué:** Mostramos componentes de selección modernos y la diferencia entre una lista normal y una `Lazy` (eficiencia).

```kotlin
@Composable
fun EjemploLazyList() {
    val items = List(20) { "Item #${it + 1}" }
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // LazyRow: Scroll horizontal eficiente
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item -> AssistChip(onClick = {}, label = { Text(item) }) }
        }

        // LazyColumn: Scroll vertical eficiente (solo renderiza lo visible)
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(items) { item ->
                Card(modifier = Modifier.fillMaxWidth()) { Text(item, modifier = Modifier.padding(8.dp)) }
            }
        }
    }
}
```
**Conclusión Final:** Compose simplifica la creación de interfaces complejas mediante la reutilización de componentes y la gestión automática del estado y las animaciones.
