package com.municipio.sistemasadm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.RegistroInspecciones} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistroInspeccionesDTO implements Serializable {

    private Long id;

    private Boolean cumpleAutorizacion;

    @NotNull
    private Integer numeroAutorizacion;

    private Boolean cumpleSenaletica;

    private Boolean cumpleConosSeguridad;

    private Boolean cumpleEtiquetado;

    private Boolean cumpleArregloCables;

    private Boolean cumplelimpiezaOrdenPozo;

    @NotNull
    private ZonedDateTime createdAt;

    private PozoDTO idPozo;

    private ProveedorDTO provedorinspeciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCumpleAutorizacion() {
        return cumpleAutorizacion;
    }

    public void setCumpleAutorizacion(Boolean cumpleAutorizacion) {
        this.cumpleAutorizacion = cumpleAutorizacion;
    }

    public Integer getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(Integer numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public Boolean getCumpleSenaletica() {
        return cumpleSenaletica;
    }

    public void setCumpleSenaletica(Boolean cumpleSenaletica) {
        this.cumpleSenaletica = cumpleSenaletica;
    }

    public Boolean getCumpleConosSeguridad() {
        return cumpleConosSeguridad;
    }

    public void setCumpleConosSeguridad(Boolean cumpleConosSeguridad) {
        this.cumpleConosSeguridad = cumpleConosSeguridad;
    }

    public Boolean getCumpleEtiquetado() {
        return cumpleEtiquetado;
    }

    public void setCumpleEtiquetado(Boolean cumpleEtiquetado) {
        this.cumpleEtiquetado = cumpleEtiquetado;
    }

    public Boolean getCumpleArregloCables() {
        return cumpleArregloCables;
    }

    public void setCumpleArregloCables(Boolean cumpleArregloCables) {
        this.cumpleArregloCables = cumpleArregloCables;
    }

    public Boolean getCumplelimpiezaOrdenPozo() {
        return cumplelimpiezaOrdenPozo;
    }

    public void setCumplelimpiezaOrdenPozo(Boolean cumplelimpiezaOrdenPozo) {
        this.cumplelimpiezaOrdenPozo = cumplelimpiezaOrdenPozo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PozoDTO getIdPozo() {
        return idPozo;
    }

    public void setIdPozo(PozoDTO idPozo) {
        this.idPozo = idPozo;
    }

    public ProveedorDTO getProvedorinspeciones() {
        return provedorinspeciones;
    }

    public void setProvedorinspeciones(ProveedorDTO provedorinspeciones) {
        this.provedorinspeciones = provedorinspeciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroInspeccionesDTO)) {
            return false;
        }

        RegistroInspeccionesDTO registroInspeccionesDTO = (RegistroInspeccionesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, registroInspeccionesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistroInspeccionesDTO{" +
            "id=" + getId() +
            ", cumpleAutorizacion='" + getCumpleAutorizacion() + "'" +
            ", numeroAutorizacion=" + getNumeroAutorizacion() +
            ", cumpleSenaletica='" + getCumpleSenaletica() + "'" +
            ", cumpleConosSeguridad='" + getCumpleConosSeguridad() + "'" +
            ", cumpleEtiquetado='" + getCumpleEtiquetado() + "'" +
            ", cumpleArregloCables='" + getCumpleArregloCables() + "'" +
            ", cumplelimpiezaOrdenPozo='" + getCumplelimpiezaOrdenPozo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", idPozo=" + getIdPozo() +
            ", provedorinspeciones=" + getProvedorinspeciones() +
            "}";
    }
}
