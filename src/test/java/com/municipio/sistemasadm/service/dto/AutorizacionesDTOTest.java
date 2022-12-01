package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutorizacionesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutorizacionesDTO.class);
        AutorizacionesDTO autorizacionesDTO1 = new AutorizacionesDTO();
        autorizacionesDTO1.setId(1L);
        AutorizacionesDTO autorizacionesDTO2 = new AutorizacionesDTO();
        assertThat(autorizacionesDTO1).isNotEqualTo(autorizacionesDTO2);
        autorizacionesDTO2.setId(autorizacionesDTO1.getId());
        assertThat(autorizacionesDTO1).isEqualTo(autorizacionesDTO2);
        autorizacionesDTO2.setId(2L);
        assertThat(autorizacionesDTO1).isNotEqualTo(autorizacionesDTO2);
        autorizacionesDTO1.setId(null);
        assertThat(autorizacionesDTO1).isNotEqualTo(autorizacionesDTO2);
    }
}
