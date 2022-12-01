package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import com.municipio.sistemasadm.domain.Pago;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import com.municipio.sistemasadm.service.dto.PagoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pago} and its DTO {@link PagoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagoMapper extends EntityMapper<PagoDTO, Pago> {
    @Mapping(
        target = "idDespliegueInfraestructuraTroncalDistribucion",
        source = "idDespliegueInfraestructuraTroncalDistribucion",
        qualifiedByName = "despliegueInfraestructuraTroncalDistribucionId"
    )
    @Mapping(
        target = "idDespliegueinfraestructuradispersion",
        source = "idDespliegueinfraestructuradispersion",
        qualifiedByName = "despliegueinfraestructuradispersionId"
    )
    PagoDTO toDto(Pago s);

    @Named("despliegueInfraestructuraTroncalDistribucionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DespliegueInfraestructuraTroncalDistribucionDTO toDtoDespliegueInfraestructuraTroncalDistribucionId(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    );

    @Named("despliegueinfraestructuradispersionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DespliegueinfraestructuradispersionDTO toDtoDespliegueinfraestructuradispersionId(
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion
    );
}
