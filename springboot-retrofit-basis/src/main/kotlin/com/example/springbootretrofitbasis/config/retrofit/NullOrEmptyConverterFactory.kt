package com.example.springbootretrofitbasis.config.retrofit

import mu.KLoggable
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NullOrEmptyConverterFactory : Converter.Factory() {

    companion object : Any(), KLoggable {
        override val logger = logger()
        const val READ_MAX_SIZE = 1024L
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, Any> {
        return Converter { responseBody ->
            val source = responseBody.source()
            source.request(READ_MAX_SIZE)
            val buffer = source.buffer

            if (buffer.size() <= 0L) {
                logger.info("if buffer is zero down, message input")
                responseBody.source().buffer.writeUtf8("{ \"message\": \"body is empty\" }")
            }

            retrofit
                .nextResponseBodyConverter<ResponseBody>(this, type, annotations)
                .convert(responseBody)
        }
    }
}
