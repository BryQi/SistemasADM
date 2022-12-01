package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DespliegueInfraestructuraTroncalDistribucionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DespliegueInfraestructuraTroncalDistribucion.class);
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion1 = new DespliegueInfraestructuraTroncalDistribucion();
        despliegueInfraestructuraTroncalDistribucion1.setId(1L);
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion2 = new DespliegueInfraestructuraTroncalDistribucion();
        despliegueInfraestructuraTroncalDistribucion2.setId(despliegueInfraestructuraTroncalDistribucion1.getId());
        assertThat(despliegueInfraestructuraTroncalDistribucion1).isEqualTo(despliegueInfraestructuraTroncalDistribucion2);
        despliegueInfraestructuraTroncalDistribucion2.setId(2L);
        assertThat(despliegueInfraestructuraTroncalDistribucion1).isNotEqualTo(despliegueInfraestructuraTroncalDistribucion2);
        despliegueInfraestructuraTroncalDistribucion1.setId(null);
        assertThat(despliegueInfraestructuraTroncalDistribucion1).isNotEqualTo(despliegueInfraestructuraTroncalDistribucion2);
    }
}
