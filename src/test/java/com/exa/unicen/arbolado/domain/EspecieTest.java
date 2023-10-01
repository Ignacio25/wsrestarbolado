package com.exa.unicen.arbolado.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.exa.unicen.arbolado.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especie.class);
        Especie especie1 = new Especie();
        especie1.setId(1L);
        Especie especie2 = new Especie();
        especie2.setId(especie1.getId());
        assertThat(especie1).isEqualTo(especie2);
        especie2.setId(2L);
        assertThat(especie1).isNotEqualTo(especie2);
        especie1.setId(null);
        assertThat(especie1).isNotEqualTo(especie2);
    }
}
