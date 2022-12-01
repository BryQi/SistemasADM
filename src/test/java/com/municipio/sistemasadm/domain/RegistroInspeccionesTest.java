package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistroInspeccionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroInspecciones.class);
        RegistroInspecciones registroInspecciones1 = new RegistroInspecciones();
        registroInspecciones1.setId(1L);
        RegistroInspecciones registroInspecciones2 = new RegistroInspecciones();
        registroInspecciones2.setId(registroInspecciones1.getId());
        assertThat(registroInspecciones1).isEqualTo(registroInspecciones2);
        registroInspecciones2.setId(2L);
        assertThat(registroInspecciones1).isNotEqualTo(registroInspecciones2);
        registroInspecciones1.setId(null);
        assertThat(registroInspecciones1).isNotEqualTo(registroInspecciones2);
    }
}
