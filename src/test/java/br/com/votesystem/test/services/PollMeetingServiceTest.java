package br.com.votesystem.test.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.domain.persistence.VotacaoSessao;
import br.com.votesystem.services.interfaces.IVotoSessaoService;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PollMeetingServiceTest {

    @Mock
    private IVotoSessaoService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Create a new PollMeeting")
    @Test
    public void shouldCreatePollMeeting() throws Exception {
        VotacaoAta minute = new VotacaoAta(001L, "Description 01", "Resume 01");
        VotacaoSessao obj = new VotacaoSessao();

        obj.setVotacaoAta(minute);
        obj.setId(000001L);
        obj.getBeginAt();

        ZonedDateTime endAt = obj.getBeginAt().plusMinutes(5L);
        obj.adjustDuration(5L);

        minute.setPoll(obj);

        when(service.add(any(VotacaoSessao.class))).thenReturn(obj);

        VotacaoSessao saved = service.add(obj);

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getVotacaoAta(), minute);
        Assertions.assertEquals(saved, minute.getPoll());
        Assertions.assertEquals(saved.getBeginAt(), obj.getBeginAt());
        Assertions.assertEquals(saved.getEndAt(), endAt);
        Assertions.assertEquals(saved.getEndAt(), obj.getEndAt());

    }

}
