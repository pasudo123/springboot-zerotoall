package com.example.springbootretrofitbasis.config.retrofit

import mu.KLoggable
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class RetrofitCustomLoggingInterceptor : Interceptor {

    companion object : Any(), KLoggable {
        override val logger = logger()
        private val UTF8 = Charset.forName("UTF-8")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startExec = System.currentTimeMillis()
        val requestLog = """
            {
                "external.request.uri": "${request.url()}",
                "external.request.method": "${request.method()}",
                "external.request.body": "${request.body()}",
            }
        """.trimIndent()
        logger.info { "\n===> [request] \n$requestLog" }

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (exception: Exception) {
            val errorLog = """
                {
                    "external.request.error.message": "${exception.message}",
                }
            """.trimIndent()
            logger.error { "\n===> [request-error] \n$errorLog" }
            throw exception
        }

        val endExec = System.currentTimeMillis()

        val responseLog = """
            {
                "external.response.request.uri": "${response.request().url()}",
                "external.response.httpStatus": "${response.code()}",
                "external.response.body": "${response.body()}",
                "external.response.latency": "${endExec - startExec}ms",
            }
        """.trimIndent()
        logger.info { "\n<=== [response] \n$responseLog" }

        return response
    }

    private fun Response.payload(): String {
        val responseBody = this.body() ?: return ""

        val source = responseBody.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer

        var charset = UTF8
        responseBody.contentType()?.let { mediaType ->
            charset = mediaType.charset(UTF8)!!
        }

        if (responseBody.contentLength() == 0L) {
            return ""
        }

        return buffer.clone().readString(charset)
    }
}
