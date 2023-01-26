package br.com.compass.pb.msorder;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Microserviço Order",
				description = "Gerenciamento de pedidos",
				version = "0.1.0",
				contact = @Contact(
						name = "Jônatas Gomes",
						email = "jonatas.gomes2014@hotmail.com",
						url = "https://github.com/Jonatas-Gomes"
				)
		), servers = @Server(url = "http://localhost:8081/api", description = "URL Base da aplicação"),
		externalDocs = @ExternalDocumentation(
				description = "Repositório da aplicação",
				url = "https://github.com/Jonatas-Gomes/PB-SpringBoot-Final-Exam"
		)
)
public class MsOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrderApplication.class, args);
	}

}
