package com.example.springbootgqlbasis.domain.item

class ItemResources {

    class Response(
        val id: Long,
        val name: String,
        val price: Double,
        val type: Item.Type
    ) {
        companion object {
            fun from(item: Item): Response {
                return item.run {
                    Response(
                        this.id!!,
                        this.name,
                        this.price,
                        this.type
                    )
                }
            }
        }
    }
}