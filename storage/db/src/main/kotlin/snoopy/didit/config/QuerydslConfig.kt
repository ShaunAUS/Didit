package snoopy.didit.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig(
    @PersistenceContext
    private val entityManager: EntityManager,
) {
    @Bean
    fun query(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}
