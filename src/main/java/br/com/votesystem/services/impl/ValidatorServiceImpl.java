package br.com.votesystem.services.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votesystem.services.interfaces.IValidatorService;

import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidatorServiceImpl implements IValidatorService {
    private final Logger logger;
    private final Validator validator;

    /**
     * Constructor
     * @param logger
     * @param validator
     */
    @Autowired
    public ValidatorServiceImpl(final Logger logger,
                                final Validator validator) {
        this.logger = logger;
        this.validator = validator;
    }

    /**
     * Validate entity
     * @param entity
     * @param <T>
     * @return violations
     */
    @Override
    public <T> Set<String> validateEntity(final T entity) {
        Set<String> violations = validator.validate(entity).stream().map(p -> p.getMessage()).collect(Collectors.toSet());

        if(violations.isEmpty()) {
           logger.info("Entity {} {} Valid", entity.getClass().toString(), entity.toString());
        } else {
            logger.warn("Entity {} {} invalid, with violations: {}", entity.getClass().toString(), entity.toString(), violations.stream().collect(Collectors.joining("\n")));
        }
        return violations;
    }

    /**
     * Check if entity valid
     * @param entity
     * @param <T>
     * @return valid
     */
    @Override
    public <T> boolean validate(final T entity) {
        return validateEntity(entity).isEmpty();
    }
}
