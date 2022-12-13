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
    @Mapping(target = "idProveedor", source = "idProveedor", qualifiedByName = "proveedorId")
    @Mapping(target = "pozo", source = "pozo", qualifiedByName = "pozoId")
    AutorizacionesDTO toDto(Autorizaciones s);

    @Named("proveedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProveedorDTO toDtoProveedorId(Proveedor proveedor);

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);
}
