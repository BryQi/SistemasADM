package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.FotoPozo;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.service.dto.FotoPozoDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FotoPozo} and its DTO {@link FotoPozoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FotoPozoMapper extends EntityMapper<FotoPozoDTO, FotoPozo> {
    @Mapping(target = "numeropozo", source = "numeropozo", qualifiedByName = "pozoNumeropozo")
    FotoPozoDTO toDto(FotoPozo s);

    @Named("pozoNumeropozo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeropozo", source = "numeropozo")
    PozoDTO toDtoPozoNumeropozo(Pozo pozo);
}
