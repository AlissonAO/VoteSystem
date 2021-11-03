package br.com.votesystem.test.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.votesystem.domain.persistence.*;
import br.com.votesystem.enuns.Voto;
import br.com.votesystem.services.interfaces.IAssociadoVotoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateVoteServiceTest {

    @Mock
    private IAssociadoVotoService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Vote on poll")
    @Test
    public void shouldCreateAssociateVote() throws Exception {
        VotacaoAta minute = new VotacaoAta(001L, "Description 01", "Resume 01");
        Associado associate = new Associado(7878L, "12345678901");
        VotacaoSessao poll = new VotacaoSessao();
        VotoAssociado vote = new VotoAssociado();

        poll.setVotacaoAta(minute);
        poll.setId(000001L);
        poll.getBeginAt();
        poll.adjustDuration(5L);

        minute.setPoll(poll);

        vote.setId(01L);
        vote.setAssociado(associate);
        vote.setVoto(Voto.AGREE);
        poll.addVoto(vote);

        vote.getPoll().updateVotos();

        when(service.add(any(VotoAssociado.class))).thenReturn(vote);

        VotoAssociado saved = service.add(vote);

        Assertions.assertEquals(saved.getId(), vote.getId());
        Assertions.assertEquals(saved.getAssociado(), associate);
        Assertions.assertEquals(saved.getPoll(), poll);
        Assertions.assertTrue(saved.getPoll().isOpen());

        Assertions.assertEquals(1L, vote.getPoll().getVotosTotal());
        Assertions.assertEquals(1L, vote.getPoll().getVotosAgrees());
        Assertions.assertNotEquals(1L, vote.getPoll().getVotosDisagrees());

    }

}
