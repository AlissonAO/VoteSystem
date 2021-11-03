package br.com.votesystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Alisson.Oliveira
 *
 */
@Configuration
public class ConfigLogger {

    /**
     * Return logger
     * @return logger
     */
    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger("votacao");
    }
}
