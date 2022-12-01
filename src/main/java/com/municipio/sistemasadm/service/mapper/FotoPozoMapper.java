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
    @Mapping(target = "idPozo", source = "idPozo", qualifiedByName = "pozoId")
    FotoPozoDTO toDto(FotoPozo s);

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);
}
