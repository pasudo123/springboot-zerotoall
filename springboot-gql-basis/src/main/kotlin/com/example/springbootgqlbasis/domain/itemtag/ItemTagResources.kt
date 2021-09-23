package com.example.springbootgqlbasis.domain.itemtag

class ItemTagResources {

    class Response(
        val id: Long,
        val name: String
    ) {
        companion object {
            fun from(itemTag: ItemTag): Response {
                return itemTag.run {
                    Response(
                        this.id!!,
                        this.name
                    )
                }
            }
        }
    }
}