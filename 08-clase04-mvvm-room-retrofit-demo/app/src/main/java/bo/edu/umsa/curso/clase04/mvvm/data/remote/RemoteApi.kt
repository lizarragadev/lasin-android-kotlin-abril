package bo.edu.umsa.curso.clase04.mvvm.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Objeto de Transferencia de Datos (DTO) para representar una tarea remota.
 */
@Serializable
data class RemoteTodoDto(
    // Identificador de la tarea en la API remota
    @SerialName("id") val id: Int = 0,
    // Título de la tarea
    @SerialName("title") val title: String,
    // Estado de finalización de la tarea
    @SerialName("completed") val completed: Boolean = false
)

/**
 * Interfaz que define los endpoints de la API remota utilizando Retrofit.
 */
interface RemoteApi {
    // Obtiene una lista de tareas, con un límite por defecto de 5
    @GET("todos")
    suspend fun getTodos(@Query("_limit") limit: Int = 5): List<RemoteTodoDto>

    // Obtiene una tarea específica por su ID
    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): RemoteTodoDto

    // Crea una nueva tarea enviando un token en la cabecera
    @POST("todos")
    suspend fun createTodo(
        @Header("X-Teacher-Token") token: String,
        @Body todo: RemoteTodoDto
    ): Response<RemoteTodoDto>

    // Actualiza una tarea existente mediante su ID
    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: RemoteTodoDto
    ): Response<RemoteTodoDto>

    // Elimina una tarea por su ID
    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Response<Unit>
}

/**
 * Fábrica para configurar y crear una instancia de RemoteApi.
 */
object RemoteServiceFactory {
    fun createApi(): RemoteApi {
        // Configura el interceptor para registrar los cuerpos de las peticiones HTTP
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        // Configura el cliente HTTP con tiempos de espera e interceptores
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        // Configura la serialización JSON ignorando claves desconocidas
        val json = Json { ignoreUnknownKeys = true }
        // Construye la instancia de Retrofit con la URL base y el convertidor JSON
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttp)
            .build()

        // Retorna la implementación de la interfaz RemoteApi generada por Retrofit
        return retrofit.create(RemoteApi::class.java)
    }
}
