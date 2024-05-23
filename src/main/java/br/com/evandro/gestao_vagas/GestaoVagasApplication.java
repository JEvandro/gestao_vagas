package br.com.evandro.gestao_vagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/*
Annotation responsive for tell to SpringBoot where he will start all the application
Defines that the class below have the method main
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GestaoVagasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVagasApplication.class, args);
	}
}