package com.example.springbootretrofitbasis.client.shortnews.model

data class ShortNewsResponse(
    val category: String,
    val data: List<DataResponse>,
    val success: String
) {

    data class DataResponse(
        val author: String?,
        val content: String?,
        val date: String?,
        val imageUrl: String?,
        val readMoreUrl: String?,
        val time: String?,
        val title: String?,
        val url: String?
    )
}