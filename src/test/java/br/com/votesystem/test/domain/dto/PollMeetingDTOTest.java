package br.com.votesystem.test.domain.dto;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.dto.VotacaoSessaoDTO;

public class PollMeetingDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldBeNotValid() {
        Long id = 0001L;
        String IdVotoAta = "00001L";
        Long duracao = 15L;
        VotacaoSessaoDTO obj = new VotacaoSessaoDTO();

        obj.setId(id.toString());
        obj.setIdVotoAta(IdVotoAta);
        obj.setDuracao(duracao + 5);

        Assertions.assertNotEquals(id, obj.getId());
        Assertions.assertNotEquals(IdVotoAta, obj.getIdVotoAta());
        Assertions.assertNotEquals(duracao, obj.getDuracao());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String id = " PM0001 ".trim().toLowerCase();
        String idVotoAta = "     MM000001   ".trim().toLowerCase();
        Long duracao= 15L;
        VotacaoSessaoDTO obj = new VotacaoSessaoDTO();

        obj.setId(id);
        obj.setIdVotoAta(idVotoAta);
        obj.setDuracao(duracao);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(idVotoAta, obj.getIdVotoAta());
        Assertions.assertEquals(duracao, obj.getDuracao());
    }

}
