package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pozo} and its DTO {@link PozoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PozoMapper extends EntityMapper<PozoDTO, Pozo> {}
