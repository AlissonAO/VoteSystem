package br.com.votesystem.test.domain.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.dto.VotacaoAtaDTO;
import br.com.votesystem.enuns.Voto;

public class MinuteMeetingDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldMinuteMeetingNotValid() {
        long id = 1L;
        String descricao = "  Description Minute 01    ";
        String resumo = "  Resume of Minute 01    ";
        Long countTotal = 30L;
        Long countAgree = 10L;
        Long countDisagree = 20L;

        VotacaoAtaDTO obj = new VotacaoAtaDTO();

        obj.setId(id);
        obj.setDescricao(descricao);
        obj.setResumo(resumo);

        obj.setVotosTotal(null);
        obj.setVotosSim(countAgree + 3);
        obj.setvotosNao(countDisagree + 4);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertNotEquals(descricao, obj.getDescricao());
        Assertions.assertNotEquals(resumo, obj.getResumo());

        Assertions.assertNotEquals(countTotal, obj.getVotosTotal());
        Assertions.assertNotEquals(countAgree, obj.getVotosSim());
        Assertions.assertNotEquals(countDisagree, obj.getVotosNao());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldMinuteMeetingValid() {
        Long id = 1L;
        String descricao = "  Description Minute 01    ".trim();
        String resumo = "  Resume of Minute 01    ".trim();
        Long votoTotal = 30L;
        Long votoSim = 10L;
        Long votoNao = 20L;

        VotacaoAtaDTO obj = new VotacaoAtaDTO();

        obj.setId(id);
        obj.setDescricao(descricao);
        obj.setResumo(resumo);

        obj.setVotosTotal(-10L);
        obj.setVotosSim(votoSim);
        obj.setvotosNao(votoNao);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(descricao, obj.getDescricao());
        Assertions.assertEquals(resumo, obj.getResumo());

        Assertions.assertEquals(votoTotal, obj.getVotosTotal());
        Assertions.assertEquals(votoSim, obj.getVotosSim());
        Assertions.assertEquals(votoNao, obj.getVotosNao());

    }

}
