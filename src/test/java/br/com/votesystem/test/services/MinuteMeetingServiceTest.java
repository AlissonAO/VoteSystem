package br.com.votesystem.test.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.services.interfaces.IVotoAtaService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MinuteMeetingServiceTest {

    @Mock
    private IVotoAtaService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Insert a new MinuteMeeting")
    @Test
    public void shouldInsertMinuteMeeting() throws Exception {
        VotacaoAta obj = new VotacaoAta(1L, "Description 01", "Resume 01");

        when(service.add(any(VotacaoAta.class))).thenReturn(obj);

        VotacaoAta saved = service.add(new VotacaoAta("Description 01", "Resume 01"));

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getDescricao(), obj.getDescricao());
        Assertions.assertEquals(saved.getResumo(), obj.getResumo());
    }

    @DisplayName("Update a MinuteMeeting")
    @Test
    public void shouldUpdateMinuteMeeting() throws Exception {
        VotacaoAta original = new VotacaoAta(1L, "Description 01", "Resume 01");
        VotacaoAta obj = new VotacaoAta(1L, "Description 02", "Resume 02");

        when(service.update(any(VotacaoAta.class))).thenReturn(obj);

        VotacaoAta saved = service.update(new VotacaoAta(1L, "Description 02", "Resume 02"));

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertNotEquals(saved.getDescricao(), original.getDescricao());
        Assertions.assertNotEquals(saved.getResumo(), original.getResumo());
        Assertions.assertEquals(saved.getDescricao(), obj.getDescricao());
        Assertions.assertEquals(saved.getResumo(), obj.getResumo());
    }

    @DisplayName("Delete a MinuteMeeting")
    @Test
    public void shouldDeleteMinuteMeeting() throws Exception {
        VotacaoAta obj = new VotacaoAta(1L, "", "");

        when(service.remove(anyLong())).thenReturn(true);
        when(service.remove(2L)).thenReturn(false);

        Assertions.assertTrue(service.remove(obj.getId()));
        Assertions.assertFalse(service.remove(2L));
    }
}
