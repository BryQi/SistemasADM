package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PozoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PozoDTO.class);
        PozoDTO pozoDTO1 = new PozoDTO();
        pozoDTO1.setId(1L);
        PozoDTO pozoDTO2 = new PozoDTO();
        assertThat(pozoDTO1).isNotEqualTo(pozoDTO2);
        pozoDTO2.setId(pozoDTO1.getId());
        assertThat(pozoDTO1).isEqualTo(pozoDTO2);
        pozoDTO2.setId(2L);
        assertThat(pozoDTO1).isNotEqualTo(pozoDTO2);
        pozoDTO1.setId(null);
        assertThat(pozoDTO1).isNotEqualTo(pozoDTO2);
    }
}
