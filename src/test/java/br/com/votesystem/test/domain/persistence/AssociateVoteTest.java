package br.com.votesystem.test.domain.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.domain.persistence.VotoAssociado;
import br.com.votesystem.enuns.Voto;

public class AssociateVoteTest {

    /**
     * Test if valid
     */
//    @Test
//    public void shouldAssociateVotoValid(){
//        VotoAssociado obj = new VotoAssociado(new Associado(), Voto.AGREE);
//
//        obj.getAssociate().setCpf("12345678901");
//        obj.setPoll(new PollMeetingPU());
//        Assertions.assertTrue(obj.isValid());
//    }
//
//    /**
//     * Test if invalid
//     */
//    @Test
//    public void shouldAssociateVotoInvalid(){
//        VotoAssociado obj = new VotoAssociado(new Associado(), Voto.AGREE);
//
//        obj.getAssociate().setCpf("    ");
//        obj.setPoll(new PollMeetingPU());
//        Assertions.assertFalse(obj.isValid());
//    }

    /**
     * Test if both AssociatePU are same
     */
    @Test
    public void shouldAssociatesSame() {
        VotoAssociado obj1 = new VotoAssociado(new Associado("123"), Voto.AGREE);
        VotoAssociado obj2 = new VotoAssociado(new Associado("123"), Voto.DISAGREE);

        Assertions.assertTrue(obj1.same(obj2));
        Assertions.assertNotEquals(obj1, obj2);
        Assertions.assertNotEquals(obj1.getAssociado(), obj2.getAssociado());

        Assertions.assertNotEquals(obj1.getVoto(), obj2.getVoto());

        obj1.setVoto(Voto.DISAGREE);
        Assertions.assertEquals(obj1.getVoto(), obj2.getVoto());
    }

    /**
     * Test if both AssociatePU are diff
     */
//    @Test
//    public void shouldAssociatesDiff() {
//        VotoAssociado obj1 = new VotoAssociado(new Associado("1234"), Voto.AGREE);
//        VotoAssociado obj2 = new VotoAssociado(new Associado("123"), Voto.DISAGREE);
//
//        Assertions.assertFalse(obj1.same(obj2));
//        Assertions.assertNotEquals(obj1, obj2);
//        Assertions.assertNotEquals(obj1.getAssociate(), obj2.getAssociate());
//    }

}
