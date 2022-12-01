package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoPozoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FotoPozoDTO.class);
        FotoPozoDTO fotoPozoDTO1 = new FotoPozoDTO();
        fotoPozoDTO1.setId(1L);
        FotoPozoDTO fotoPozoDTO2 = new FotoPozoDTO();
        assertThat(fotoPozoDTO1).isNotEqualTo(fotoPozoDTO2);
        fotoPozoDTO2.setId(fotoPozoDTO1.getId());
        assertThat(fotoPozoDTO1).isEqualTo(fotoPozoDTO2);
        fotoPozoDTO2.setId(2L);
        assertThat(fotoPozoDTO1).isNotEqualTo(fotoPozoDTO2);
        fotoPozoDTO1.setId(null);
        assertThat(fotoPozoDTO1).isNotEqualTo(fotoPozoDTO2);
    }
}
