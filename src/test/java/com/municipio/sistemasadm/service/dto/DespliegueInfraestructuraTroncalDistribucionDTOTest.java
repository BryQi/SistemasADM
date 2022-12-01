package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DespliegueInfraestructuraTroncalDistribucionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DespliegueInfraestructuraTroncalDistribucionDTO.class);
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO1 = new DespliegueInfraestructuraTroncalDistribucionDTO();
        despliegueInfraestructuraTroncalDistribucionDTO1.setId(1L);
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO2 = new DespliegueInfraestructuraTroncalDistribucionDTO();
        assertThat(despliegueInfraestructuraTroncalDistribucionDTO1).isNotEqualTo(despliegueInfraestructuraTroncalDistribucionDTO2);
        despliegueInfraestructuraTroncalDistribucionDTO2.setId(despliegueInfraestructuraTroncalDistribucionDTO1.getId());
        assertThat(despliegueInfraestructuraTroncalDistribucionDTO1).isEqualTo(despliegueInfraestructuraTroncalDistribucionDTO2);
        despliegueInfraestructuraTroncalDistribucionDTO2.setId(2L);
        assertThat(despliegueInfraestructuraTroncalDistribucionDTO1).isNotEqualTo(despliegueInfraestructuraTroncalDistribucionDTO2);
        despliegueInfraestructuraTroncalDistribucionDTO1.setId(null);
        assertThat(despliegueInfraestructuraTroncalDistribucionDTO1).isNotEqualTo(despliegueInfraestructuraTroncalDistribucionDTO2);
    }
}
