package com.example.springbootmongobasis.domain.user

import com.example.springbootmongobasis.domain.BaseDocument
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User(
    val userUniqueId: String,
    val name: String
) : BaseDocument() {

    @Id
    var id: ObjectId = ObjectId()
        protected set
}
