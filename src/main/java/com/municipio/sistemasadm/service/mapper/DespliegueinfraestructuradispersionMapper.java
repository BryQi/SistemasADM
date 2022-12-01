package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Despliegueinfraestructuradispersion} and its DTO {@link DespliegueinfraestructuradispersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DespliegueinfraestructuradispersionMapper
    extends EntityMapper<DespliegueinfraestructuradispersionDTO, Despliegueinfraestructuradispersion> {
    @Mapping(target = "pozos", source = "pozos", qualifiedByName = "pozoIdSet")
    @Mapping(
        target = "idDespliegueInfraestructuraTroncalDistribucion",
        source = "idDespliegueInfraestructuraTroncalDistribucion",
        qualifiedByName = "despliegueInfraestructuraTroncalDistribucionId"
    )
    @Mapping(target = "idProveedor", source = "idProveedor", qualifiedByName = "proveedorId")
    DespliegueinfraestructuradispersionDTO toDto(Despliegueinfraestructuradispersion s);

    @Mapping(target = "removePozo", ignore = true)
    Despliegueinfraestructuradispersion toEntity(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO);

    @Named("pozoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PozoDTO toDtoPozoId(Pozo pozo);

    @Named("pozoIdSet")
    default Set<PozoDTO> toDtoPozoIdSet(Set<Pozo> pozo) {
        return pozo.stream().map(this::toDtoPozoId).collect(Collectors.toSet());
    }

    @Named("despliegueInfraestructuraTroncalDistribucionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DespliegueInfraestructuraTroncalDistribucionDTO toDtoDespliegueInfraestructuraTroncalDistribucionId(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    );

    @Named("proveedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProveedorDTO toDtoProveedorId(Proveedor proveedor);
}
