package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Infraestructura;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.InfraestructuraDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Infraestructura} and its DTO {@link InfraestructuraDTO}.
 */
@Mapper(componentModel = "spring")
public interface InfraestructuraMapper extends EntityMapper<InfraestructuraDTO, Infraestructura> {
    @Mapping(target = "idProveedor", source = "idProveedor", qualifiedByName = "proveedorId")
    @Mapping(target = "pozos", source = "pozos", qualifiedByName = "pozoIdSet")
    InfraestructuraDTO toDto(Infraestructura s);

    @Mapping(target = "removePozo", ignore = true)
    Infraestructura toEntity(InfraestructuraDTO infraestructuraDTO);

    @Named("proveedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProveedorDTO toDtoProveedorId(Proveedor proveedor);

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);

    @Named("pozoIdSet")
    default Set<PozoDTO> toDtoPozoIdSet(Set<Pozo> pozo) {
        return pozo.stream().map(this::toDtoPozoId).collect(Collectors.toSet());
    }
}
