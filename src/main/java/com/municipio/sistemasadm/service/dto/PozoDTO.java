package com.municipio.sistemasadm.service.dto;

import com.municipio.sistemasadm.domain.enumeration.TipoPozo;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.municipio.sistemasadm.domain.Pozo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PozoDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeropozo;

    @NotNull
    private String direccion;

    private TipoPozo tipopozo;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private String longitud;

    @NotNull
    private String latitud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeropozo() {
        return numeropozo;
    }

    public void setNumeropozo(String numeropozo) {
        this.numeropozo = numeropozo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoPozo getTipopozo() {
        return tipopozo;
    }

    public void setTipopozo(TipoPozo tipopozo) {
        this.tipopozo = tipopozo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PozoDTO)) {
            return false;
        }

        PozoDTO pozoDTO = (PozoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pozoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PozoDTO{" +
            "id=" + getId() +
            ", numeropozo='" + getNumeropozo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", tipopozo='" + getTipopozo() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", longitud='" + getLongitud() + "'" +
            ", latitud='" + getLatitud() + "'" +
            "}";
    }
}
