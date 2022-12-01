package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutorizacionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autorizaciones.class);
        Autorizaciones autorizaciones1 = new Autorizaciones();
        autorizaciones1.setId(1L);
        Autorizaciones autorizaciones2 = new Autorizaciones();
        autorizaciones2.setId(autorizaciones1.getId());
        assertThat(autorizaciones1).isEqualTo(autorizaciones2);
        autorizaciones2.setId(2L);
        assertThat(autorizaciones1).isNotEqualTo(autorizaciones2);
        autorizaciones1.setId(null);
        assertThat(autorizaciones1).isNotEqualTo(autorizaciones2);
    }
}
