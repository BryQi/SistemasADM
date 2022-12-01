package com.municipio.sistemasadm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PozoMapperTest {

    private PozoMapper pozoMapper;

    @BeforeEach
    public void setUp() {
        pozoMapper = new PozoMapperImpl();
    }
}
