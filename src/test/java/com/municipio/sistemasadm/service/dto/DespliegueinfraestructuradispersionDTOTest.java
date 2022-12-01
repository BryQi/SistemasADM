package com.municipio.sistemasadm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DespliegueinfraestructuradispersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DespliegueinfraestructuradispersionDTO.class);
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO1 = new DespliegueinfraestructuradispersionDTO();
        despliegueinfraestructuradispersionDTO1.setId(1L);
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO2 = new DespliegueinfraestructuradispersionDTO();
        assertThat(despliegueinfraestructuradispersionDTO1).isNotEqualTo(despliegueinfraestructuradispersionDTO2);
        despliegueinfraestructuradispersionDTO2.setId(despliegueinfraestructuradispersionDTO1.getId());
        assertThat(despliegueinfraestructuradispersionDTO1).isEqualTo(despliegueinfraestructuradispersionDTO2);
        despliegueinfraestructuradispersionDTO2.setId(2L);
        assertThat(despliegueinfraestructuradispersionDTO1).isNotEqualTo(despliegueinfraestructuradispersionDTO2);
        despliegueinfraestructuradispersionDTO1.setId(null);
        assertThat(despliegueinfraestructuradispersionDTO1).isNotEqualTo(despliegueinfraestructuradispersionDTO2);
    }
}
