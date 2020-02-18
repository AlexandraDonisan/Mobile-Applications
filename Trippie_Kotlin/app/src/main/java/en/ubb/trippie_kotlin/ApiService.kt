package en.ubb.trippie_kotlin

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService{
    @GET("sightseeings/")
    fun getAllSightseeings() : Observable<List<Word>>

    @GET("sightseeings/{id}")
    fun getSightseeing(@Query("id") id: Int) : Observable<Word>

    @POST("sightseeings/")
    fun addSightseeing(@Body sightseeing : Word) : Observable<Word>

    @DELETE("sightseeings/{id}")
    fun deleteSightseeing(@Path("id") id: Int) : Observable<Word>

    @PUT("sightseeings/{id}")
    fun updateightseeing(@Path("id") id: Int, @Body sightseeing : Word) : Observable<Word>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.30.113.226:8080/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}