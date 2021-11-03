package br.com.votesystem.test.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.services.interfaces.IAssociadoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateServiceTest {

    @Mock
    private IAssociadoService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Insert a new Associate")
    @Test
    public void shouldInsertAssociate() throws Exception {
        Associado obj = new Associado(123L, "12345678901");

        when(service.add(any(Associado.class))).thenReturn(obj);

        Associado saved = service.add(obj);

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getCpf(), obj.getCpf());
    }

    @DisplayName("Update a Associate")
    @Test
    public void shouldUpdateAssociate() throws Exception {
        Associado obj = new Associado(123L, "12345678901");
        Associado saved = new Associado(123L, "109876543221");

        when(service.update(any(Associado.class))).thenReturn(saved);

        Associado save = service.update(obj);


        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getCpf(), save.getCpf());
        Assertions.assertNotEquals(saved.getCpf(), obj.getCpf());
    }

    @DisplayName("Delete a Associate")
    @Test
    public void shouldDeleteAssociate() throws Exception {
        Associado obj = new Associado(123L, "12345678901");

        when(service.remove(anyLong())).thenReturn(true);

        Assertions.assertTrue(service.remove(obj.getId()));
    }
}
