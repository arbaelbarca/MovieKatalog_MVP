package com.arbaelbarca.listmovieskatalog.network

import com.arbaelbarca.listmovieskatalog.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkApi private constructor() {

    val api: ApiServices
    private val credentials: String? = null



    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_MOVIES)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        api = retrofit.create(ApiServices::class.java)

    }

    companion object {
        private var networkApi: NetworkApi? = null


        private fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request = chain.request()
                        val authenticatedRequest = request.newBuilder()
                                .header("Authorization", "Bearer tokenBearer").build()// tokenbearre = disi dengan token bearernyaa dari db movie
                        chain.proceed(authenticatedRequest)
                    }
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build()
        }

        private val loggingInterceptor: HttpLoggingInterceptor
            get() {
                val interceptor = HttpLoggingInterceptor()
                return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            }

        val instance: NetworkApi
            get() {
                if (networkApi == null)
                    networkApi = NetworkApi()
                return networkApi!!
            }
    }


}
