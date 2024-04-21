package com.example.springbootgrpcbasis.config

import com.example.springbootgrpcbasis.client.MockClient
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.protobuf.ProtoConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "app.client")
class ClientDiConfiguration {

    val mockApi = ExternalApi()
    val protobufHeader = Headers.Builder()
        .add("Content-Type", "application/x-protobuf;charset=utf-8")
        .add("Accept", "application/x-protobuf;charset=utf-8")
        .build()

    @Bean
    fun mockClient(): MockClient {
        val httpClient = httpClient(mockApi, protobufHeader)

        return Retrofit.Builder()
            .baseUrl(mockApi.host)
            .addConverterFactory(ProtoConverterFactory.create())
            .callFactory(httpClient)
            .build()
            .create(MockClient::class.java)
    }

    companion object {

        private fun httpClient(api: ExternalApi, headers: Headers): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder()
                .connectTimeout(api.connectionTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(api.readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(api.writeTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder().headers(headers)
                    val request = builder.build()
                    chain.proceed(request)
                }.build()
        }
    }
}

open class ExternalApi {
    lateinit var host: String
    var connectionTimeout by Delegates.notNull<Long>()
    var readTimeout by Delegates.notNull<Long>()
    var writeTimeout by Delegates.notNull<Long>()
    var properties = mutableMapOf<String, Any>()
}
