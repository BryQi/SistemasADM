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
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    @Mapping(target = "numeropozos", source = "numeropozos", qualifiedByName = "pozoNumeropozoSet")
    InfraestructuraDTO toDto(Infraestructura s);

    @Mapping(target = "removeNumeropozo", ignore = true)
    Infraestructura toEntity(InfraestructuraDTO infraestructuraDTO);

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

    @Named("pozoNumeropozoSet")
    default Set<PozoDTO> toDtoPozoNumeropozoSet(Set<Pozo> pozo) {
        return pozo.stream().map(this::toDtoPozoNumeropozo).collect(Collectors.toSet());
    }
}
