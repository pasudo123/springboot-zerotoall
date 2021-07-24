package com.example.springbootpageablebasis.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("search")
class PageableController {

    @GetMapping("request-params")
    fun requestParams(
        @RequestParam(name = "page", defaultValue = "1") page: Int,
        @RequestParam(name = "size", defaultValue = "25") size: Int,
        @RequestParam(name = "sortBy", defaultValue = "createdAt") sortBy: String,
        @RequestParam(name = "sortOrder", defaultValue = "DESC") sortOrder: SortOrder
    ): ResponseEntity<Pageable> = ResponseEntity.ok(
        Pageable(
            page = page,
            size = size,
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    )

    @GetMapping("query-map")
    fun queryMap(
        @RequestParam query: Map<String, String>
    ): ResponseEntity<Pageable> = ResponseEntity.ok(
        query.toPageable()
    )

    @GetMapping("pojo")
    fun pojo(
        pageable: Pageable
    ): ResponseEntity<Pageable> = ResponseEntity.ok(pageable)
}

fun Map<String, String>.toPageable(): Pageable = this.let {
    Pageable(
        page = it["page"]?.toInt() ?: 1,
        size = it["size"]?.toInt() ?: 25,
        sortBy = it["sortBy"] ?: "createdAt",
        sortOrder = SortOrder.valueOf(it["sortOrder"] ?: SortOrder.DESC.name)
    )
}