package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DespliegueinfraestructuradispersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Despliegueinfraestructuradispersion.class);
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion1 = new Despliegueinfraestructuradispersion();
        despliegueinfraestructuradispersion1.setId(1L);
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion2 = new Despliegueinfraestructuradispersion();
        despliegueinfraestructuradispersion2.setId(despliegueinfraestructuradispersion1.getId());
        assertThat(despliegueinfraestructuradispersion1).isEqualTo(despliegueinfraestructuradispersion2);
        despliegueinfraestructuradispersion2.setId(2L);
        assertThat(despliegueinfraestructuradispersion1).isNotEqualTo(despliegueinfraestructuradispersion2);
        despliegueinfraestructuradispersion1.setId(null);
        assertThat(despliegueinfraestructuradispersion1).isNotEqualTo(despliegueinfraestructuradispersion2);
    }
}
