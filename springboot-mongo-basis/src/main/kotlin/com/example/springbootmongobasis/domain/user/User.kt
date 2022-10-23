package com.example.springbootmongobasis.domain.user

import com.example.springbootmongobasis.domain.BaseDocument
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User(
    val userUniqueId: String,
    val name: String,
    val sequence: Long? = null
) : BaseDocument() {

    @Id
    @JsonDeserialize
    var id: ObjectId = ObjectId()
        protected set
}
