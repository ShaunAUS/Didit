package snoopy.didit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiditApplication

fun main(args: Array<String>) {
    runApplication<DiditApplication>(*args)
}
