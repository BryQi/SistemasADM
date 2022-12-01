package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Autorizaciones;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.domain.RegistroInspecciones;
import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Autorizaciones} and its DTO {@link AutorizacionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AutorizacionesMapper extends EntityMapper<AutorizacionesDTO, Autorizaciones> {
    @Mapping(target = "registroInspecciones", source = "registroInspecciones", qualifiedByName = "registroInspeccionesId")
    @Mapping(target = "idProveedor", source = "idProveedor", qualifiedByName = "proveedorId")
    AutorizacionesDTO toDto(Autorizaciones s);

    @Named("registroInspeccionesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegistroInspeccionesDTO toDtoRegistroInspeccionesId(RegistroInspecciones registroInspecciones);

    @Named("proveedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProveedorDTO toDtoProveedorId(Proveedor proveedor);
}
