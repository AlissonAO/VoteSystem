package br.com.votesystem.services.impl;

/**
 * @author Alisson.Oliveira
 */
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.votesystem.domain.dto.*;
import br.com.votesystem.services.interfaces.ISendMessage;
import br.com.votesystem.util.Action;

import static br.com.votesystem.util.ApplicationConstants.*;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageKafkaImpl implements ISendMessage {
    private ObjectMapper mapper = null;
    private final Logger logger;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor
     * @param kafkaTemplate
     * @param logger
     */
    @Autowired
    public SendMessageKafkaImpl(final KafkaTemplate<String, String> kafkaTemplate, final Logger logger) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = new ObjectMapper();
        this.logger = logger;
    }

    /**
     * Send message to the topic
     * @param <T>
     * @param value
     * @throws Exception
     */
    public <T> void send(final Action<T> value) throws Exception {
        String message = mapper.writeValueAsString(value);
        String topic = null;

        if(value.getObj() instanceof AssociadoDTO) {
            topic = TOPIC_ASSOCIADO;
        } else if(value.getObj() instanceof VotacaoAtaDTO) {
            topic = TOPIC_VOTO_ATA;
        } else if(value.getObj() instanceof VotacaoSessaoDTO) {
            topic = TOPIC_VOTO_SESSAO;
        } else if(value.getObj() instanceof AssociadoVoteDTO) {
            topic = TOPIC_VOTE;
        }

        kafkaTemplate.send(topic, message);
        logger.info("enviando mensagem para o Kafka: topic:{}, message:{}", topic, message);
    }
}
