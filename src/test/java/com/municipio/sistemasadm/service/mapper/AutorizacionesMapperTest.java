package com.municipio.sistemasadm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutorizacionesMapperTest {

    private AutorizacionesMapper autorizacionesMapper;

    @BeforeEach
    public void setUp() {
        autorizacionesMapper = new AutorizacionesMapperImpl();
    }
}
