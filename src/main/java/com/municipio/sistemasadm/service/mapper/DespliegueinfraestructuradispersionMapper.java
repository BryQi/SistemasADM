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
    @Mapping(target = "nombreRuta", source = "nombreRuta", qualifiedByName = "despliegueInfraestructuraTroncalDistribucionNombreRuta")
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    @Mapping(target = "numeropozos", source = "numeropozos", qualifiedByName = "pozoNumeropozoSet")
    DespliegueinfraestructuradispersionDTO toDto(Despliegueinfraestructuradispersion s);

    @Mapping(target = "removeNumeropozo", ignore = true)
    Despliegueinfraestructuradispersion toEntity(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO);

    @Named("despliegueInfraestructuraTroncalDistribucionNombreRuta")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreRuta", source = "nombreRuta")
    DespliegueInfraestructuraTroncalDistribucionDTO toDtoDespliegueInfraestructuraTroncalDistribucionNombreRuta(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    );

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
