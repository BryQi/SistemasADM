package com.municipio.sistemasadm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfraestructuraMapperTest {

    private InfraestructuraMapper infraestructuraMapper;

    @BeforeEach
    public void setUp() {
        infraestructuraMapper = new InfraestructuraMapperImpl();
    }
}
