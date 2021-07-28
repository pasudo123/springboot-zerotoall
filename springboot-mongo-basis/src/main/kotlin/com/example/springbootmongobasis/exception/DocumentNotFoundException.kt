package com.example.springbootmongobasis.exception

class DocumentNotFoundException (
    override val message: String
) : RuntimeException(message)