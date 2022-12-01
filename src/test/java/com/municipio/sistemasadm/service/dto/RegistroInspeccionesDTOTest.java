package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistroInspeccionesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroInspeccionesDTO.class);
        RegistroInspeccionesDTO registroInspeccionesDTO1 = new RegistroInspeccionesDTO();
        registroInspeccionesDTO1.setId(1L);
        RegistroInspeccionesDTO registroInspeccionesDTO2 = new RegistroInspeccionesDTO();
        assertThat(registroInspeccionesDTO1).isNotEqualTo(registroInspeccionesDTO2);
        registroInspeccionesDTO2.setId(registroInspeccionesDTO1.getId());
        assertThat(registroInspeccionesDTO1).isEqualTo(registroInspeccionesDTO2);
        registroInspeccionesDTO2.setId(2L);
        assertThat(registroInspeccionesDTO1).isNotEqualTo(registroInspeccionesDTO2);
        registroInspeccionesDTO1.setId(null);
        assertThat(registroInspeccionesDTO1).isNotEqualTo(registroInspeccionesDTO2);
    }
}
