package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoPozoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoPozo.class);
        FotoPozo fotoPozo1 = new FotoPozo();
        fotoPozo1.setId(1L);
        FotoPozo fotoPozo2 = new FotoPozo();
        fotoPozo2.setId(fotoPozo1.getId());
        assertThat(fotoPozo1).isEqualTo(fotoPozo2);
        fotoPozo2.setId(2L);
        assertThat(fotoPozo1).isNotEqualTo(fotoPozo2);
        fotoPozo1.setId(null);
        assertThat(fotoPozo1).isNotEqualTo(fotoPozo2);
    }
}
