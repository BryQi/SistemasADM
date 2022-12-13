package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.domain.RegistroInspecciones;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegistroInspecciones} and its DTO {@link RegistroInspeccionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegistroInspeccionesMapper extends EntityMapper<RegistroInspeccionesDTO, RegistroInspecciones> {
    @Mapping(target = "idPozo", source = "idPozo", qualifiedByName = "pozoId")
    @Mapping(target = "provedorinspeciones", source = "provedorinspeciones", qualifiedByName = "proveedorId")
    RegistroInspeccionesDTO toDto(RegistroInspecciones s);

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);

    @Named("proveedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProveedorDTO toDtoProveedorId(Proveedor proveedor);
}
