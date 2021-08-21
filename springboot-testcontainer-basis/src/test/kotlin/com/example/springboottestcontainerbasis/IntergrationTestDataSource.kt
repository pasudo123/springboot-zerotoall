package com.example.springboottestcontainerbasis

import com.example.springboottestcontainerbasis.CustomMysqlContainer.Companion.MYSQL_CONTAINER
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import javax.sql.DataSource

@TestConfiguration
class IntergrationTestDataSource {

    @Bean
    @DependsOn("customMysqlContainer")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url(MYSQL_CONTAINER.jdbcUrl)
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .username(MYSQL_CONTAINER.username)
            .password(MYSQL_CONTAINER.password)
            .build()
    }
}