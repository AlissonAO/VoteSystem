package br.com.votesystem;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.votesystem.config.ConfigLogger;

/**
 * 
 * @author Alisson.Oliveira
 *
 */

@SpringBootApplication
public class Application {

	// Aqui se inicia a aplicação spring
    public static void main(final String[] args) {
        Logger logger = new ConfigLogger().getLogger();
        logger.info("Iniciando a Aplicação...");
        SpringApplication.run(Application.class, args);
    }
}
