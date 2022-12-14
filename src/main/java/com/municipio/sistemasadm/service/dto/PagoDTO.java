package com.municipio.sistemasadm.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Pago} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PagoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaPago;

    @NotNull
    private Integer pago;

    @NotNull
    private ZonedDateTime createdAt;

    private ProveedorDTO razonSocial;

    private DespliegueInfraestructuraTroncalDistribucionDTO calculoValorPago;

    private DespliegueinfraestructuradispersionDTO calculoValorPagoD;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPago() {
        return pago;
    }

    public void setPago(Integer pago) {
        this.pago = pago;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProveedorDTO getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(ProveedorDTO razonSocial) {
        this.razonSocial = razonSocial;
    }

    public DespliegueInfraestructuraTroncalDistribucionDTO getCalculoValorPago() {
        return calculoValorPago;
    }

    public void setCalculoValorPago(DespliegueInfraestructuraTroncalDistribucionDTO calculoValorPago) {
        this.calculoValorPago = calculoValorPago;
    }

    public DespliegueinfraestructuradispersionDTO getCalculoValorPagoD() {
        return calculoValorPagoD;
    }

    public void setCalculoValorPagoD(DespliegueinfraestructuradispersionDTO calculoValorPagoD) {
        this.calculoValorPagoD = calculoValorPagoD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagoDTO)) {
            return false;
        }

        PagoDTO pagoDTO = (PagoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pagoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagoDTO{" +
            "id=" + getId() +
            ", fechaPago='" + getFechaPago() + "'" +
            ", pago=" + getPago() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", razonSocial=" + getRazonSocial() +
            ", calculoValorPago=" + getCalculoValorPago() +
            ", calculoValorPagoD=" + getCalculoValorPagoD() +
            "}";
    }
}
