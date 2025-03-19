package snoopy.didit.feign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["snoopy.didit"])
class FeignConfiguration
