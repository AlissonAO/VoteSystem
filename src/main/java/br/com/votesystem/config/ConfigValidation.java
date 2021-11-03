package br.com.votesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 
 * @author Alisson.Oliveira
 *
 */
@Configuration
public class ConfigValidation {

    private final ValidatorFactory factory;
    private final Validator validator;

    /**
     * Constructor
     */
    public ConfigValidation() {
        this.factory = Validation.buildDefaultValidatorFactory();
        this.validator  = factory.getValidator();
    }

    /**
     * Return validator
     * @return validator
     */
    @Bean
    public Validator getValidator() {
        return this.validator;
    }
}
