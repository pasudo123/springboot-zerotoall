package com.example.springbootmongobasis.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

//@Configuration
//class MongoDbConfiguration : AbstractMongoClientConfiguration() {
//
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    @Value("\${spring.data.mongodb.database}")
//    private lateinit var databaseName: String
//
//    @Value("\${spring.data.mongodb.username}")
//    private lateinit var username: String
//
//    @Value("\${spring.data.mongodb.password}")
//    private lateinit var password: String
//
//    override fun getDatabaseName(): String {
//        return databaseName
//    }
//
//    override fun mongoClient(): MongoClient {
//        val connectionInfo = ConnectionString("mongodb://$username:$password@localhost:27018")
//
//        val setting = MongoClientSettings.builder()
//            .applyConnectionString(connectionInfo)
//            .build()
//
//        return MongoClients.create(setting)
//    }
//}