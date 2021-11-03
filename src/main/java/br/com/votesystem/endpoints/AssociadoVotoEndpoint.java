package br.com.votesystem.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votesystem.domain.dto.AssociadoVoteDTO;
import br.com.votesystem.services.interfaces.IAssociadoVotoService;
import br.com.votesystem.services.interfaces.IEndpointService;

import static br.com.votesystem.util.ApplicationConstants.ENDPOINT_VOTO_V1;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ENDPOINT_VOTO_V1)
public class AssociadoVotoEndpoint {

    private IAssociadoVotoService associadoVotoService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_VOTO_V1 + "/%s";

    /**
     * Constructor
     *
     * @param associadoVotoService
     * @param endpointService
     */
    @Autowired
    public AssociadoVotoEndpoint(final IAssociadoVotoService associadoVotoService,
                                 final IEndpointService endpointService) {
        this.associadoVotoService = associadoVotoService;
        this.endpointService = endpointService;
    }

    /**
     * Add a poll
     *
     * @param value
     * @return poll added
     */
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final AssociadoVoteDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, associadoVotoService.add(value), request));
    }

}
