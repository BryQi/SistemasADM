package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DespliegueInfraestructuraTroncalDistribucion} and its DTO {@link DespliegueInfraestructuraTroncalDistribucionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DespliegueInfraestructuraTroncalDistribucionMapper
    extends EntityMapper<DespliegueInfraestructuraTroncalDistribucionDTO, DespliegueInfraestructuraTroncalDistribucion> {
    @Mapping(target = "pozos", source = "pozos", qualifiedByName = "pozoIdSet")
    DespliegueInfraestructuraTroncalDistribucionDTO toDto(DespliegueInfraestructuraTroncalDistribucion s);

    @Mapping(target = "removePozo", ignore = true)
    DespliegueInfraestructuraTroncalDistribucion toEntity(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    );

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);

    @Named("pozoIdSet")
    default Set<PozoDTO> toDtoPozoIdSet(Set<Pozo> pozo) {
        return pozo.stream().map(this::toDtoPozoId).collect(Collectors.toSet());
    }
}
