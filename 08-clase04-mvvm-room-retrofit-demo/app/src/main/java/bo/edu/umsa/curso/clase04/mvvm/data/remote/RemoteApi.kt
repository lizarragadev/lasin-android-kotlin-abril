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

@Serializable
data class RemoteTodoDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("title") val title: String,
    @SerialName("completed") val completed: Boolean = false
)

interface RemoteApi {
    @GET("todos")
    suspend fun getTodos(@Query("_limit") limit: Int = 5): List<RemoteTodoDto>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): RemoteTodoDto

    @POST("todos")
    suspend fun createTodo(
        @Header("X-Teacher-Token") token: String,
        @Body todo: RemoteTodoDto
    ): Response<RemoteTodoDto>

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: RemoteTodoDto
    ): Response<RemoteTodoDto>

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): Response<Unit>
}

object RemoteServiceFactory {
    fun createApi(): RemoteApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttp)
            .build()

        return retrofit.create(RemoteApi::class.java)
    }
}
