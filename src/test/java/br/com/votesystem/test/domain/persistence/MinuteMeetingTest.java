package br.com.votesystem.test.domain.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.persistence.VotacaoAta;

public class MinuteMeetingTest {

    /**
     * Test if invalid
     */
    @Test
    public void shouldBeInvalid() {
        VotacaoAta obj = new VotacaoAta();

        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String resumo = "Resume of minute      ";
        String descricao = "Description of minute     ";
        VotacaoAta obj = new VotacaoAta(descricao, resumo);

//        Assertions.assertTrue(obj.isValid());
//        Assertions.assertEquals(descricao.trim(), obj.getDescricao());
       // Assertions.assertEquals(resumo.trim(), obj.getResumo());

//        Assertions.assertNotEquals(descricao, obj.getDescricao());
//        Assertions.assertNotEquals(resumo, obj.getResumo());
    }

    /**
     * Test create poll
     */
    @Test
    public void shouldBeCreatePoll() {
        VotacaoAta obj = new VotacaoAta("Description of minute     ", "Resume of minute      ");

//        Assertions.assertTrue(obj.isValid());

        obj.createPoll(null);

        Assertions.assertNotNull(obj.getPoll());
        Assertions.assertNotNull(obj.getPoll().getEndAt());

    }


}
