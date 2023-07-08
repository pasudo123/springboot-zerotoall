package com.example.springbooterrorbasis

import com.example.springbooterrorbasis.config.JpaAuditingConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("repo")
@DataJpaTest
@Import(
    value = [JpaAuditingConfiguration::class, RepositoryTestConfiguration::class]
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTestSupport {

    @Autowired
    protected lateinit var testEntityManager: TestEntityManager
}
