package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DespliegueInfraestructuraTroncalDistribucion} and its DTO {@link DespliegueInfraestructuraTroncalDistribucionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DespliegueInfraestructuraTroncalDistribucionMapper
    extends EntityMapper<DespliegueInfraestructuraTroncalDistribucionDTO, DespliegueInfraestructuraTroncalDistribucion> {
    @Mapping(target = "pozos", source = "pozos", qualifiedByName = "pozoNumeropozoSet")
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    DespliegueInfraestructuraTroncalDistribucionDTO toDto(DespliegueInfraestructuraTroncalDistribucion s);

    @Mapping(target = "removePozo", ignore = true)
    DespliegueInfraestructuraTroncalDistribucion toEntity(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    );

    @Named("pozoNumeropozo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeropozo", source = "numeropozo")
    PozoDTO toDtoPozoNumeropozo(Pozo pozo);

    @Named("pozoNumeropozoSet")
    default Set<PozoDTO> toDtoPozoNumeropozoSet(Set<Pozo> pozo) {
        return pozo.stream().map(this::toDtoPozoNumeropozo).collect(Collectors.toSet());
    }

    @Named("proveedorRazonSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razonSocial", source = "razonSocial")
    ProveedorDTO toDtoProveedorRazonSocial(Proveedor proveedor);
}
