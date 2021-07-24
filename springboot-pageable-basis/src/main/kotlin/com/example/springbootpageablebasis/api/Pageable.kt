package com.example.springbootpageablebasis.api

data class Pageable(
    val page: Int = 1,
    val size: Int = 25,
    val sortBy: String = "createdAt",
    val sortOrder: SortOrder = SortOrder.DESC
)