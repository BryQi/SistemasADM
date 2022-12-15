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
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    @Mapping(target = "numeropozo", source = "numeropozo", qualifiedByName = "pozoNumeropozo")
    RegistroInspeccionesDTO toDto(RegistroInspecciones s);

    @Named("proveedorRazonSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razonSocial", source = "razonSocial")
    ProveedorDTO toDtoProveedorRazonSocial(Proveedor proveedor);

    @Named("pozoNumeropozo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeropozo", source = "numeropozo")
    PozoDTO toDtoPozoNumeropozo(Pozo pozo);
}
