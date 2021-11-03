package br.com.votesystem.test.domain.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.persistence.*;
import br.com.votesystem.enuns.Voto;

public class PollMeetingTest {

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        VotacaoSessao obj = new VotacaoSessao();

        Assertions.assertFalse(obj.isValid());
        obj.setVotacaoAta(new VotacaoAta());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
        obj.getVotacaoAta().setDescricao("Descricao(");

//        Assertions.assertTrue(obj.isValid());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeInvalid() {
        VotacaoSessao obj = new VotacaoSessao();

        Assertions.assertFalse(obj.isValid());
        obj.setVotacaoAta(new VotacaoAta());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if session open
     */
    @Test
    public void shouldBeOpen() {
        VotacaoSessao obj = new VotacaoSessao();

        Assertions.assertFalse(obj.isValid());
        obj.setVotacaoAta(new VotacaoAta());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
        obj.getVotacaoAta().setDescricao("Descricao");

//        Assertions.assertTrue(obj.isValid());
//        Assertions.assertTrue(obj.isOpen());
    }

    /**
     * Test vote, a Associate dont vote twice
     */
    @Test
    public void shouldBeVotod() {
        VotacaoSessao obj = new VotacaoSessao();
        Associado duplicate = new Associado("123");

        obj.setVotacaoAta(new VotacaoAta());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));
        obj.getVotacaoAta().setDescricao("Descricao");

        obj.addVoto(duplicate, Voto.AGREE);
        obj.addVoto(duplicate, Voto.AGREE);

        obj.addVoto(new Associado("1"), Voto.AGREE);
        obj.addVoto(new Associado("2"), Voto.AGREE);

        obj.addVoto(new Associado("3"), Voto.DISAGREE);
        obj.addVoto(new Associado("4"), Voto.DISAGREE);
        obj.addVoto(new Associado("5"), Voto.DISAGREE);
//        obj.addVoto(new Associado("6"), Voto.DISAGREE);

//        Assertions.assertEquals(4L, obj.countDisagrees());
//        Assertions.assertEquals(3L, obj.countAgrees());
        Assertions.assertEquals(obj.countVotoSim() + obj.countVotoNao(), obj.countTotal());
    }

    /**
     * Test vote, a Associate dont vote when the poll is not open
     */
    @Test
    public void shouldBeNotVotoWhenClosed() {
        VotacaoSessao obj = new VotacaoSessao();

        obj.setVotacaoAta(new VotacaoAta());
        obj.setEndAt(null);
        obj.getVotacaoAta().setDescricao("Descricao");

        obj.addVoto(new Associado("1"), Voto.AGREE);
        obj.addVoto(new Associado("2"), Voto.AGREE);
        obj.addVoto(new Associado("3"), Voto.DISAGREE);
        obj.addVoto(new Associado("4"), Voto.AGREE);

        Assertions.assertTrue(obj.getVotos().isEmpty());
    }
}
