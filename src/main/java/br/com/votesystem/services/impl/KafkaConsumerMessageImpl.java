package br.com.votesystem.services.impl;

import static br.com.votesystem.util.ApplicationConstants.GROUP_ID;
import static br.com.votesystem.util.ApplicationConstants.TOPIC_ASSOCIADO;
import static br.com.votesystem.util.ApplicationConstants.TOPIC_VOTE;
import static br.com.votesystem.util.ApplicationConstants.TOPIC_VOTO_ATA;
import static br.com.votesystem.util.ApplicationConstants.TOPIC_VOTO_SESSAO;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.votesystem.domain.dto.AssociadoDTO;
import br.com.votesystem.domain.dto.AssociadoVoteDTO;
import br.com.votesystem.domain.dto.VotacaoAtaDTO;
import br.com.votesystem.domain.dto.VotacaoSessaoDTO;
import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.enuns.CRUD;
import br.com.votesystem.services.interfaces.IAssociadoService;
import br.com.votesystem.services.interfaces.IAssociadoVotoService;
import br.com.votesystem.services.interfaces.IConsumerMessage;
import br.com.votesystem.services.interfaces.IVotoAtaService;
import br.com.votesystem.services.interfaces.IVotoSessaoService;
import br.com.votesystem.util.Action;

@Component
public class KafkaConsumerMessageImpl implements IConsumerMessage {

    private final ObjectMapper mapper;
    private final Logger logger;
    private final IAssociadoService associadoService;
    private final IVotoAtaService votoAtaService;
    private final IVotoSessaoService votoSessaoService;
    private final IAssociadoVotoService associadoVotoService;

    /**
     * Constructor
     * @param mapper
     * @param logger
     * @param associadoService
     * @param votoAtaService
     * @param votoSessaoService
     * @param associadoVotoService
     */
    public KafkaConsumerMessageImpl(final ObjectMapper mapper,
                                    final Logger logger,
                                    final IAssociadoService associadoService,
                                    final IVotoAtaService votoAtaService,
                                    final IVotoSessaoService votoSessaoService,
                                    final IAssociadoVotoService associadoVotoService) {
        this.mapper = mapper;
        this.logger = logger;
        this.associadoService = associadoService;
        this.votoAtaService = votoAtaService;
        this.votoSessaoService = votoSessaoService;
        this.associadoVotoService = associadoVotoService;
    }

    @KafkaListener(topics = {TOPIC_ASSOCIADO, TOPIC_VOTO_ATA, TOPIC_VOTO_SESSAO, TOPIC_VOTE}, groupId = GROUP_ID)
    public void consume(@Payload final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic) {
        logger.info("Recebendo do topic: {}, mesg: {}", topic, message);

        switch (topic) {
            case TOPIC_ASSOCIADO:
                handleAssociado(buildAction(message, AssociadoDTO.class));
            break;
            case TOPIC_VOTO_ATA:
                handleVotoAta(buildAction(message, VotacaoAtaDTO.class));
                break;
            case TOPIC_VOTO_SESSAO:
                handleVotoSessao(buildAction(message, VotacaoSessaoDTO.class));
                break;
            case TOPIC_VOTE:
                handleVoto(buildAction(message, AssociadoVoteDTO.class));
                break;
        }
    }

    /**
     * Build action from json
     * @param message
     * @param clazz
     * @param <T>
     * @return action
     */
    private <T> Action<T> buildAction(final String message, final Class<T> clazz) {
        Action<T> action = new Action<>();

        try {
            JsonNode root = mapper.readTree(message);

            action.setObj(mapper.treeToValue(root.at("/obj"), clazz));
            action.setAction(mapper.treeToValue(root.at("/action"), CRUD.class));
        } catch(Exception e) {
            action = null;
            logger.error("Não foi possível descobrir a Action", e);
        }

        return action;
    }

    
    private void handleAssociado(final Action<AssociadoDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    associadoService.add(new Associado(action.getObj().getId(), action.getObj().getCpf()));
                    break;
                case UPDATE:
                    associadoService.update(new Associado(action.getObj().getId(), action.getObj().getCpf()));
                    break;
                case DELETE:
                    associadoService.remove(action.getObj().getId());
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on AssociateDTO", e);
        }
    }

    /**
     * Handle actions for MinuteMeeting
     * @param action
     */
    private void handleVotoAta(final Action<VotacaoAtaDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    votoAtaService.add(new VotacaoAta(action.getObj().getId(), action.getObj().getDescricao(), action.getObj().getResumo()));
                    break;
                case UPDATE:
                    votoAtaService.update(new VotacaoAta(action.getObj().getId(), action.getObj().getDescricao(), action.getObj().getResumo()));
                    break;
                case DELETE:
                    votoAtaService.remove(action.getObj().getId());
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR ", e);
        }
    }

 
    private void handleVotoSessao(final Action<VotacaoSessaoDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    votoSessaoService.add(votoSessaoService.convert(action.getObj()));
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on PollMeetingDTO", e);
        }
    }

  
    private void handleVoto(final Action<AssociadoVoteDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    associadoVotoService.add(associadoVotoService.convert(action.getObj()));
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on AssociateVotoDTO", e);
        }
    }
}
