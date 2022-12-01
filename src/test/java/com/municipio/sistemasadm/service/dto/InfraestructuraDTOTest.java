package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfraestructuraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfraestructuraDTO.class);
        InfraestructuraDTO infraestructuraDTO1 = new InfraestructuraDTO();
        infraestructuraDTO1.setId(1L);
        InfraestructuraDTO infraestructuraDTO2 = new InfraestructuraDTO();
        assertThat(infraestructuraDTO1).isNotEqualTo(infraestructuraDTO2);
        infraestructuraDTO2.setId(infraestructuraDTO1.getId());
        assertThat(infraestructuraDTO1).isEqualTo(infraestructuraDTO2);
        infraestructuraDTO2.setId(2L);
        assertThat(infraestructuraDTO1).isNotEqualTo(infraestructuraDTO2);
        infraestructuraDTO1.setId(null);
        assertThat(infraestructuraDTO1).isNotEqualTo(infraestructuraDTO2);
    }
}
