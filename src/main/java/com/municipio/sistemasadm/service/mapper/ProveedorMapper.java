package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proveedor} and its DTO {@link ProveedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {}
