package br.com.votesystem.test.domain.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.votesystem.domain.persistence.Associado;

public class AssociateTest {

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String cpf = "123.456.789-01";
        String cpfDigits = "12345678901";
        Associado obj = new Associado();

        obj.setCpf(cpf);
        Assertions.assertEquals(cpfDigits, obj.getCpf());
    }

    /**
     * Test if invalid
     */
    @Test
    public void shouldBeInvalid() {
        Associado obj = new Associado();

        obj.setCpf("  ");

        obj.setCpf("");

        obj.setCpf(null);
    }

    /**
     * Test if both AssociatePU are same (identification)
     */
    @Test
    public void shouldBeSame() {
        Associado obj1 = new Associado("12345678901");
        Associado obj2 = new Associado("12345678901");

        Assertions.assertNotEquals(obj1, obj2);
    }
}
