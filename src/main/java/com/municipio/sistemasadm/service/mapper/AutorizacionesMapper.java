package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Autorizaciones;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Autorizaciones} and its DTO {@link AutorizacionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AutorizacionesMapper extends EntityMapper<AutorizacionesDTO, Autorizaciones> {
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    @Mapping(target = "numeropozo", source = "numeropozo", qualifiedByName = "pozoNumeropozo")
    AutorizacionesDTO toDto(Autorizaciones s);

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
