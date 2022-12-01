package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfraestructuraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infraestructura.class);
        Infraestructura infraestructura1 = new Infraestructura();
        infraestructura1.setId(1L);
        Infraestructura infraestructura2 = new Infraestructura();
        infraestructura2.setId(infraestructura1.getId());
        assertThat(infraestructura1).isEqualTo(infraestructura2);
        infraestructura2.setId(2L);
        assertThat(infraestructura1).isNotEqualTo(infraestructura2);
        infraestructura1.setId(null);
        assertThat(infraestructura1).isNotEqualTo(infraestructura2);
    }
}
