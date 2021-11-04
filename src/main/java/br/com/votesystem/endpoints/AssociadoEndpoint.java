package br.com.votesystem.endpoints;

import static br.com.votesystem.util.ApplicationConstants.ENDPOINT_ASSOCIADO_V1;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votesystem.domain.dto.AssociadoDTO;
import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.services.interfaces.IAssociadoService;
import br.com.votesystem.services.interfaces.IEndpointService;

@RestController
@RequestMapping(ENDPOINT_ASSOCIADO_V1)
public class AssociadoEndpoint {

    private IAssociadoService associadoService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_ASSOCIADO_V1 + "/%s";

    /**
     * Constructor
     *
     */
    @Autowired
    public AssociadoEndpoint(final IAssociadoService associadoService, final IEndpointService endpointService) {
        this.associadoService = associadoService;
        this.endpointService = endpointService;
    }

   
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final AssociadoDTO value, final HttpServletRequest request) throws Exception {
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associadoService.add(value), request);
        return ResponseEntity.ok(uri);
    }

  
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody() final AssociadoDTO value, final HttpServletRequest request) throws Exception {
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associadoService.update(value), request);
        return ResponseEntity.ok(uri);
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable(value = "id", required = true) final Long id, final HttpServletRequest request) throws Exception {
        AssociadoDTO value = new AssociadoDTO(id);
        String uri = endpointService.process(RETRIEVE_TEMPLATE, associadoService.remove(value), request);
        return ResponseEntity.ok(uri);
    }

   
    @GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<Collection<AssociadoDTO>> findByIdentification(@PathVariable(value = "cpf", required = true) final String cpf) {
        return ResponseEntity.ok(convert(associadoService.findByCpf(cpf)));
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDTO> findById(@PathVariable(value = "id", required = true) final String id) throws Exception {
        AssociadoDTO dto = associadoService.convert(associadoService.findById(Long.valueOf(id)));
        HttpStatus status = dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(dto, status);
    }

   
//    @GetMapping
//    public Page<AssociadoDTO> findAll(final Pageable pageable) {
//        Page<Associado> pagePU = associadoService.findAll(pageable);
//        Page<AssociadoDTO> page = new PageImpl<>(convert(pagePU.getContent()), pagePU.getPageable(), pagePU.getTotalElements());
//
//        return page;
//    }

 
    private List<AssociadoDTO> convert(Associado values) {
        return (List<AssociadoDTO>) values;
        
    }
}
