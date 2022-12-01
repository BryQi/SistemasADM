package com.municipio.sistemasadm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroInspeccionesMapperTest {

    private RegistroInspeccionesMapper registroInspeccionesMapper;

    @BeforeEach
    public void setUp() {
        registroInspeccionesMapper = new RegistroInspeccionesMapperImpl();
    }
}
