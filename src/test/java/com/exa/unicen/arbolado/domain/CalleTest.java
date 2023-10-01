package com.exa.unicen.arbolado.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.exa.unicen.arbolado.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calle.class);
        Calle calle1 = new Calle();
        calle1.setId(1L);
        Calle calle2 = new Calle();
        calle2.setId(calle1.getId());
        assertThat(calle1).isEqualTo(calle2);
        calle2.setId(2L);
        assertThat(calle1).isNotEqualTo(calle2);
        calle1.setId(null);
        assertThat(calle1).isNotEqualTo(calle2);
    }
}
