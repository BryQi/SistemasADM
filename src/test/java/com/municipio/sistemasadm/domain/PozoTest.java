package com.municipio.sistemasadm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.municipio.sistemasadm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PozoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pozo.class);
        Pozo pozo1 = new Pozo();
        pozo1.setId(1L);
        Pozo pozo2 = new Pozo();
        pozo2.setId(pozo1.getId());
        assertThat(pozo1).isEqualTo(pozo2);
        pozo2.setId(2L);
        assertThat(pozo1).isNotEqualTo(pozo2);
        pozo1.setId(null);
        assertThat(pozo1).isNotEqualTo(pozo2);
    }
}
