package com.municipio.sistemasadm.service.mapper;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import com.municipio.sistemasadm.domain.Pago;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import com.municipio.sistemasadm.service.dto.PagoDTO;
import com.municipio.sistemasadm.service.dto.ProveedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pago} and its DTO {@link PagoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagoMapper extends EntityMapper<PagoDTO, Pago> {
    @Mapping(target = "razonSocial", source = "razonSocial", qualifiedByName = "proveedorRazonSocial")
    @Mapping(
        target = "calculoValorPago",
        source = "calculoValorPago",
        qualifiedByName = "despliegueInfraestructuraTroncalDistribucionCalculoValorPago"
    )
    @Mapping(
        target = "calculoValorPagoD",
        source = "calculoValorPagoD",
        qualifiedByName = "despliegueinfraestructuradispersionCalculoValorPagoD"
    )
    PagoDTO toDto(Pago s);

    @Named("proveedorRazonSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razonSocial", source = "razonSocial")
    ProveedorDTO toDtoProveedorRazonSocial(Proveedor proveedor);

    @Named("despliegueInfraestructuraTroncalDistribucionCalculoValorPago")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "calculoValorPago", source = "calculoValorPago")
    DespliegueInfraestructuraTroncalDistribucionDTO toDtoDespliegueInfraestructuraTroncalDistribucionCalculoValorPago(
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion
    );

    @Named("despliegueinfraestructuradispersionCalculoValorPagoD")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "calculoValorPagoD", source = "calculoValorPagoD")
    DespliegueinfraestructuradispersionDTO toDtoDespliegueinfraestructuradispersionCalculoValorPagoD(
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion
    );
}
