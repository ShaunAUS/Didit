package snoopy.didit.swagger.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component
import snoopy.didit.swagger.annotation.ApiDoc

@Component
class SwaggerExampleObject {
    @Value("classpath:/static/swagger")
    private lateinit var swaggerDir: Resource

    @Bean
    fun exampleJsonValueMaker(): List<io.swagger.v3.oas.models.examples.Example> {
        val exampleList = mutableListOf<io.swagger.v3.oas.models.examples.Example>()
        val resourcePatternResolver = PathMatchingResourcePatternResolver()
        val resources = resourcePatternResolver.getResources("classpath:/static/swagger/*.json")

        resources.forEach { resource ->
            val jsonContent = resource.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONParser().parse(jsonContent) as JSONObject

            jsonObject.keys.forEach { key ->
                exampleList.add(
                    io.swagger.v3.oas.models.examples.Example().apply {
                        value = jsonObject[key]
                        description = key.toString()
                    },
                )
            }
        }
        return exampleList
    }
}

@Configuration
class SwaggerConfig(
    private val swaggerExampleObject: SwaggerExampleObject,
) {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Didit Project")
                    .description("Didit API docs")
                    .version("1.0.0"),
            )
    }

    @Bean
    fun apiDocOperationCustomizer(): OperationCustomizer {
        val examples =
            swaggerExampleObject.exampleJsonValueMaker()
                .associateBy { it.description }

        return OperationCustomizer { operation, handlerMethod ->
            val apiDoc = handlerMethod.getMethodAnnotation(ApiDoc::class.java)
            if (apiDoc != null) {
                operation.summary(apiDoc.summary)
                    .description(apiDoc.description)
                    .responses(
                        ApiResponses()
                            .addApiResponse("200", createApiResponse("성공", examples[apiDoc.success]))
                            .addApiResponse("600", createApiResponse("실패", examples[apiDoc.error])),
                    )
            }
            operation
        }
    }

    private fun createApiResponse(
        description: String,
        example: io.swagger.v3.oas.models.examples.Example?,
    ): ApiResponse {
        return ApiResponse()
            .description(description)
            .content(
                Content().addMediaType("application/json", MediaType().example(example?.value)),
            )
    }
}
