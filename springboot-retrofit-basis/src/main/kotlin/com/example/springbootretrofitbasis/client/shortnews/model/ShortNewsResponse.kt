package com.example.springbootretrofitbasis.client.shortnews.model

data class ShortNewsResponse(
    val total: Int,
    val articles: List<Article>,
//    val category: String,
//    val data: List<DataResponse>,
//    val success: Boolean,
//    val error: String?
) {

    data class Article(
        val title: String,
        val description: String,
        val author_name: String,
        val source_name: String,
        val source_url: String,
        val image_url: String,
        val created_at: Long,
        val inshorts_url: String
    )

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
