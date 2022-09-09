package com.example.springbootjdbcbasis.domain.person

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.time.LocalDate
import java.time.LocalTime

@Repository
class PersonRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    /**
     * insert
     */
    fun insertPersonBulk(persons: List<Person>) {
        val query = """
            INSERT INTO person 
                (`name`, `email`, `remark`,`created_date`, `created_time`) 
            VALUES 
                (?, ?, ?, ?, ?)
        """.trimIndent()


        persons.chunked(BATCH_SIZE).forEach { chunkPersons ->

            jdbcTemplate.batchUpdate(query, object: BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    val person = chunkPersons[i]

                    ps.setString(1, person.name)
                    ps.setString(2, person.email)
                    ps.setString(3, person.remark)
                    ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()))
                    ps.setTime(5, java.sql.Time.valueOf(LocalTime.now()))
                }

                override fun getBatchSize(): Int {
                    return chunkPersons.size
                }
            })
        }
    }

    /**
     * key duplicate -> update or insert
     */
    fun upsertPersonBulk(persons: List<Person>) {
        val query = """
            INSERT INTO person 
                (`name`, `email`, `remark`,`created_date`, `created_time`) 
            VALUES 
                (?, ?, ?, ?, ?) 
            ON DUPLICATE KEY UPDATE remark = ?
        """.trimIndent()


        persons.chunked(BATCH_SIZE).forEach { chunkPersons ->

            jdbcTemplate.batchUpdate(query, object: BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    val person = chunkPersons[i]

                    ps.setString(1, person.name)
                    ps.setString(2, person.email)
                    ps.setString(3, person.remark)
                    ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()))
                    ps.setTime(5, java.sql.Time.valueOf(LocalTime.now()))

                    // key 충돌 시, 입력되는 리마크
                    ps.setString(6, person.remark)
                }

                override fun getBatchSize(): Int {
                    return chunkPersons.size
                }
            })
        }
    }

    /**
     * update
     */
    fun updatePersonBulk(persons: List<Person>) {
        val query = """
            UPDATE 
                person 
            SET 
                remark = ?, created_date = ?, created_time = ? 
            WHERE 
                name = ? AND email = ?
        """.trimIndent()


        persons.chunked(BATCH_SIZE).forEach { chunkPersons ->

            jdbcTemplate.batchUpdate(query, object: BatchPreparedStatementSetter {
                override fun setValues(ps: PreparedStatement, i: Int) {
                    val person = chunkPersons[i]

                    // update 되는 값
                    ps.setString(1, person.remark)
                    ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()))
                    ps.setTime(3, java.sql.Time.valueOf(LocalTime.now()))

                    ps.setString(4, person.name)
                    ps.setString(5, person.email)
                }

                override fun getBatchSize(): Int {
                    return chunkPersons.size
                }
            })
        }
    }

    fun deleteAll(): Boolean {
        val query = """
            DELETE FROM person
        """.trimIndent()

        return jdbcTemplate.update(query) >= 1
    }

    companion object {
        private const val BATCH_SIZE = 3000
    }
}