package br.com.votesystem.services.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.votesystem.services.interfaces.IEndpointService;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
/**
 * 
 * @author Alisson.Oliveira
 *
 */
@Component
public class EndpointServiceImpl implements IEndpointService {

    private final Logger logger;

    /**
     * Constructor
     *
     * @param logger
     */
    @Autowired
    public EndpointServiceImpl(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Create uri and log
     *
     * @param endpoint
     * @param id
     * @param request
     * @return uri
     */
    public String process(final String endpoint, final String id, final HttpServletRequest request) {
        String uri = String.format(endpoint, id);
        logger.info("requisicao {} de {} {} para {}", uri, request.getRequestURI(), request.getRemoteAddr(), ZonedDateTime.now());

        return uri;
    }

}
