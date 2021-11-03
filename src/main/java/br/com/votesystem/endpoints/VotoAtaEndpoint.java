package br.com.votesystem.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.votesystem.domain.dto.VotacaoAtaDTO;
import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.services.interfaces.IEndpointService;
import br.com.votesystem.services.interfaces.IVotoAtaService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static br.com.votesystem.util.ApplicationConstants.ENDPOINT_VOTO_ATA_V1;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ENDPOINT_VOTO_ATA_V1)
public class VotoAtaEndpoint {

    private IVotoAtaService votoAtaService;
    private IEndpointService endpointService;
    private static final String RETRIEVE_TEMPLATE = ENDPOINT_VOTO_ATA_V1 + "/%s";

    /**
     * Constructor
     *
     * @param votoAtaService
     * @param endpointService
     */
    @Autowired
    public VotoAtaEndpoint(final IVotoAtaService votoAtaService, final IEndpointService endpointService) {
        this.votoAtaService = votoAtaService;
        this.endpointService = endpointService;
    }

    /**
     * Add a minuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @PostMapping("/")
    public ResponseEntity<String> add(@Valid @RequestBody final VotacaoAtaDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, votoAtaService.add(value), request));
    }

    /**
     * Update a minuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody() final VotacaoAtaDTO value, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, votoAtaService.update(value), request));
    }

    /**
     * Remove a minuteMeeting
     *
     * @param id
     * @return minuteMeeting added
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable(value = "id", required = true) String id, final HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(endpointService.process(RETRIEVE_TEMPLATE, votoAtaService.remove(new VotacaoAtaDTO(Long.valueOf(id))), request));
    }


    /**
     * Return minuteMeeting by id
     *
     * @param id
     * @return minuteMeeting
     */
    @GetMapping("/{id}")
    public ResponseEntity<VotacaoAtaDTO> findById(@PathVariable(value = "id", required = true) final String id) throws Exception {
        VotacaoAtaDTO dto = votoAtaService.convert(votoAtaService.findById(Long.valueOf(id)));
        HttpStatus status = dto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(dto, status);
    }

    /**
     * Return minuteMeetings
     *
     * @return minuteMeetings
     */
//    @GetMapping
//    public Page<VotacaoAtaDTO> findAll(final Pageable pageable) {
//        Page<VotacaoAta> pagePU = minuteMeetingService.findAll(pageable);
//        Page<VotacaoAtaDTO> page = new PageImpl<>(convert(pagePU.getContent()), pagePU.getPageable(), pagePU.getTotalElements());
//
//        return page;
//    }

    /**
     * Convert MinuteMeetingPUs to MinuteMeetingDTOs
     * @param values
     * @return list
     */
    private List<VotacaoAtaDTO> convert(final Collection<VotacaoAta> values) {
        return votoAtaService.convert(values).stream().collect(Collectors.toList());
    }

}
