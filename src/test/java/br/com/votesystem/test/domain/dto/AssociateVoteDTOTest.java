package br.com.votesystem.test.domain.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.dto.AssociadoVoteDTO;
import br.com.votesystem.enuns.Voto;

public class AssociateVoteDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldAssociateVoteNotValid() {
        String id = "1";
        String associateId = "ASSC001";
        String minuteMeetingId = "MIN001";

        AssociadoVoteDTO obj = new AssociadoVoteDTO();

        obj.setId(id);
        obj.setAssociateId(associateId);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setVote(Voto.AGREE);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertNotEquals(associateId, obj.getAssociateId());
        Assertions.assertNotEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertEquals(Voto.AGREE, obj.getVote());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldAssociateVoteValid() {
        String id = "1";
        String associateId = "ASSC001".toLowerCase();
        String minuteMeetingId = "MIN001".toLowerCase();

        AssociadoVoteDTO obj = new AssociadoVoteDTO();

        obj.setId(id);
        obj.setAssociateId(associateId);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setVote(Voto.AGREE);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(associateId, obj.getAssociateId());
        Assertions.assertEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertEquals(Voto.AGREE, obj.getVote());
    }

}
