package com.municipio.sistemasadm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DespliegueInfraestructuraTroncalDistribucionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreRuta;

    @NotNull
    private String descripcionRuta;

    @NotNull
    private Double metrajeInicial;

    @NotNull
    private Double metrajeFinal;

    @NotNull
    private Double calculoValorPago;

    @NotNull
    private ZonedDateTime createdAt;

    private Set<PozoDTO> pozos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getDescripcionRuta() {
        return descripcionRuta;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    public Double getMetrajeInicial() {
        return metrajeInicial;
    }

    public void setMetrajeInicial(Double metrajeInicial) {
        this.metrajeInicial = metrajeInicial;
    }

    public Double getMetrajeFinal() {
        return metrajeFinal;
    }

    public void setMetrajeFinal(Double metrajeFinal) {
        this.metrajeFinal = metrajeFinal;
    }

    public Double getCalculoValorPago() {
        return calculoValorPago;
    }

    public void setCalculoValorPago(Double calculoValorPago) {
        this.calculoValorPago = calculoValorPago;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PozoDTO> getPozos() {
        return pozos;
    }

    public void setPozos(Set<PozoDTO> pozos) {
        this.pozos = pozos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DespliegueInfraestructuraTroncalDistribucionDTO)) {
            return false;
        }

        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = (DespliegueInfraestructuraTroncalDistribucionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, despliegueInfraestructuraTroncalDistribucionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DespliegueInfraestructuraTroncalDistribucionDTO{" +
            "id=" + getId() +
            ", nombreRuta='" + getNombreRuta() + "'" +
            ", descripcionRuta='" + getDescripcionRuta() + "'" +
            ", metrajeInicial=" + getMetrajeInicial() +
            ", metrajeFinal=" + getMetrajeFinal() +
            ", calculoValorPago=" + getCalculoValorPago() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", pozos=" + getPozos() +
            "}";
    }
}
