package com.exa.unicen.arbolado.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.exa.unicen.arbolado.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArbolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arbol.class);
        Arbol arbol1 = new Arbol();
        arbol1.setId(1L);
        Arbol arbol2 = new Arbol();
        arbol2.setId(arbol1.getId());
        assertThat(arbol1).isEqualTo(arbol2);
        arbol2.setId(2L);
        assertThat(arbol1).isNotEqualTo(arbol2);
        arbol1.setId(null);
        assertThat(arbol1).isNotEqualTo(arbol2);
    }
}
